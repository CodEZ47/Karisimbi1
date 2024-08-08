#!/usr/bin/bash

USER_STORE="./data/user-store.txt"

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
registerUser() {
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

    newLine="$email,$uuid,$role,$hPassword,$firstName,$lastName,$dob,$isHivPositive,$diagnosisDate,$isOnArt,$artStartDate,$countryCode"

    # Find the line with the email
    found_line=$(grep "^$email" "$USER_STORE")
    if [ -z "$found_line" ]; then
        echo "Error: User with email $email does not exist."
        return
    fi

    # Extract the stored UUID from the found line
    stored_uuid=$(echo "$found_line" | awk -F'[ ,]' '{print $2}')

    # Replace the line with the new content
    sed -i "s/^$email.*\$/$newLine/" "$USER_STORE"

    echo "User details updated successfully."
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
        registerUser $2 $3 $4 $5 $6 $7 $8 $9 ${10} ${11} ${12} ${13}
        ;;
    hashPassword)
        hash_password $2
        ;;
    *)
        echo "Usage: $0 {onBoardUser|login|verifyUUID|registerUser|hash_password} args"
        ;;
esac
