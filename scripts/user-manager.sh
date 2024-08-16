#!/usr/bin/bash

USER_STORE="./data/user-store.txt"
DESTINATION_DIR="./downloads"
ALL_USERS_CSV_FILE="$DESTINATION_DIR/all_users_data.csv"
LIFE_EXPECTANCY="./data/life-expectancy.csv"

# echo "Arguments: $@"

# Function to hash passwords
function hash_password() {
    echo -n "$1" | sha256sum | awk '{print $1}'
}

# Function to initialize the first admin user
function initializeFirstAdmin() {
    uuid=$(uuidgen)
    email="admin@gmail.com"
    role="Admin"
    hPassword=$(hash_password "admin")
    firstName="Super"
    lastName="Admin"

    echo "$email,$uuid,$role,$hPassword,$firstName,$lastName" >> $USER_STORE
}

# Function to onboard a new user
function onBoardUser() {
    email=$1
    role=$2
    echo "Registering user with email: $email and role: $role"

    if [[ -z "$email" || -z "$role" ]]; then
        echo "Error: Email and role are required."
        exit 1
    fi

    uuid=$(uuidgen)
    if echo "$email,$uuid,$role" >> $USER_STORE; then
        echo "User registered with UUID: $uuid"
        exit 0
    else
        echo "Error: Failed to write to user store."
        exit 1
    fi
}

# Function to login a user
function login_user() {
    email=$1
    password=$2
    hashed_password=$(hash_password "$password")

    # echo "Logging in user with email: $email"

    while IFS= read -r line; do
        stored_email=$(echo "$line" | awk -F, '{print $1}')
        stored_role=$(echo "$line" | awk -F, '{print $3}')
        stored_hashed_password=$(echo "$line" | awk -F, '{print $4}')
        
        # echo "Stored email: $stored_email, Stored role: $stored_role, Stored hashed password: $stored_hashed_password, Hashed password: $hashed_password"
        if [[ "$stored_email" == "$email" && "$stored_hashed_password" == "$hashed_password" ]]; then
            echo "$line";
            return
        fi
    done < "$USER_STORE"

    # Return an empty string if login fails
    echo ""
}

# Function to verify UUID
function verifyUUID() {
    uuid=$1
    email=$2

    while IFS=, read -r stored_email stored_uuid stored_role stored_hashed_password; do
        if [[ "$stored_email" == "$email" && "$stored_uuid" == "$uuid" ]]; then
            echo "$stored_role"
            return
        fi
    done < $USER_STORE

    echo ""
}

# Function to register additional user details
function registerUser() {
    firstName=$1
    lastName=$2
    email=$3
    hPassword=$4
    dob=$5
    isHivPositive=$6
    diagnosisDate=$7
    isOnArt=$8
    artStartDate=$9
    countryCode=${10}
    uuid=${11}
    role=${12}
    expectedLifeSpan=${13}

    newLine="$email,$uuid,$role,$hPassword,$firstName,$lastName,$dob,$isHivPositive,$diagnosisDate,$isOnArt,$artStartDate,$countryCode,$expectedLifeSpan"

    found_line=$(grep "^$email" "$USER_STORE")
    if [ -z "$found_line" ]; then
        echo "Error: User with email $email does not exist."
        return
    fi
    stored_uuid=$(echo "$found_line" | awk -F'[ ,]' '{print $2}')

    sed -i "s/^$email.*\$/$newLine/" "$USER_STORE"

    echo "User details updated successfully."
}

function registerAdmin(){
    email=$1
    role=$2
    hPassword=$3
    firstName=$4
    lastName=$5

    found_line=$(grep "^$email" "$USER_STORE")
    if [ -z "$found_line" ]; then
        echo "Error: User with email $email does not exist."
        return
    fi
    stored_uuid=$(echo "$found_line" | awk -F'[ ,]' '{print $2}')

    newLine="$email,$stored_uuid,$role,$hPassword,$firstName,$lastName"

    sed -i "s/^$email.*\$/$newLine/" "$USER_STORE"

    echo "User details updated successfully."
}


function fetchUserByUUID(){
    $uuid=$1

    found_line = $(awk -F, -v uuid="$uuid" '$2 == uuid' $USER_STORE)

    if [ -z "$found_line" ]; then
        echo "Error: User with uuid $uuid does not exist."
        exit 1
        return
    fi
    echo "$found_line"

}

function download_all_user_data(){
    awk 'BEGIN {OFS=","} {print $0}' "$USER_STORE" > "$ALL_USERS_CSV_FILE"

    if [ $? -eq 0 ] && [ -s "$ALL_USERS_CSV_FILE" ]; then
        echo "All users' data has been compiled into $ALL_USERS_CSV_FILE."
        exit 0
    else
        echo "Failed to compile users' data."
        exit 1
    fi
}

function get_country_code() {
    country=$1
    countryCode=$(awk -F, -v country="$country" '
    BEGIN {IGNORECASE=1}
    $1 == country {print $5; exit}
    ' "$LIFE_EXPECTANCY")

    if [ -z "$countryCode" ]; then
        echo "NOT_FOUND"
    else
        echo "$countryCode"
    fi
}

# Function to get the life expectancy for a country code
function get_life_expectancy() {
    local countryCode=$1
    local lifeExpectancy=$(awk -F, -v countryCode="$countryCode" '
    $6 == countryCode {print $7; exit}
    ' "$LIFE_EXPECTANCY")

    if [ -z "$lifeExpectancy" ]; then
        echo "NOT_FOUND"
    else
        echo "$lifeExpectancy"
    fi
}

function calculate_statistics() {
    user_store_file="$USER_STORE"
    output_csv="$DESTINATION_DIR/life_expectancy_stats.csv"
    declare -a numbers

    while IFS=',' read -r email uuid role password first_name last_name dob is_hiv_positive diagnosis_date is_on_art art_start_date country_code life_expectancy; do
        if [ -n "$life_expectancy" ]; then
            numbers+=("$life_expectancy")
        fi
    done < "$user_store_file"

    if [ ${#numbers[@]} -eq 0 ]; then
        echo "No valid life expectancy values found in the user store."
        return
    fi

    count=${#numbers[@]}

    sorted_numbers=($(printf '%s\n' "${numbers[@]}" | sort -n))

    sum=0
    for num in "${sorted_numbers[@]}"; do
        sum=$((sum + num))
    done
    average=$((sum / count))

    if (( count % 2 == 1 )); then
        median=${sorted_numbers[$((count / 2))]}
    else
        mid=$((count / 2))
        median=$(((sorted_numbers[mid-1] + sorted_numbers[mid]) / 2))
    fi

    p25_index=$(( (count - 1) * 25 / 100 ))
    p25=${sorted_numbers[$p25_index]}

    p75_index=$(( (count - 1) * 75 / 100 ))
    p75=${sorted_numbers[$p75_index]}

    mkdir -p "$(dirname "$output_csv")"
    {
        echo "Statistic,Value"
        echo "Average,$average"
        echo "Median,$median"
        echo "25th Percentile,$p25"
        echo "75th Percentile,$p75"
    } > "$output_csv"

    echo "Statistics have been written to $output_csv"
}



# Check if user store file exists, if not, create and initialize with first admin
if [[ ! -f $USER_STORE ]]; then
    mkdir -p "$(dirname "$USER_STORE")"
    touch $USER_STORE
    initializeFirstAdmin
fi


# Case statement to handle different commands
case $1 in
    onBoardUser)
        onBoardUser $2 $3
        ;;
    login)
        login_user $2 $3
        ;;
    verifyUUID)
        verifyUUID $2 $3
        ;;
    registerUser)
        registerUser $2 $3 $4 $5 $6 $7 $8 $9 ${10} ${11} ${12} ${13} ${14}
        ;;
    hashPassword)
        hash_password $2
        ;;
    registerAdmin)
        registerAdmin $2 $3 $4 $5 $6
        ;;
    fetchUserByUUID)
        fetchUserByUUID $2
        ;;
    download_all_user_data)
        download_all_user_data
        ;;
    getCountryCode)
        get_country_code $2
        ;;
    getLifeExpectancy)
        get_life_expectancy $2
        ;;
    calculateStatistics)
        calculate_statistics
        ;;
    *)
        echo "Usage: $0 {onBoardUser|login|verifyUUID|registerUser|hash_password|registerAdmin|fetchUserByUUID|download_all_user_data|getCountryCode|getLifeExpectancy|calculateStatistics} args"
        ;;
esac
