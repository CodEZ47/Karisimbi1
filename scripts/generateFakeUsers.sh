#!/bin/bash

FIRST_NAMES=("John" "Jane" "Alice" "Bob" "Carol" "David" "Emma" "Frank" "Grace" "Hank")
LAST_NAMES=("Smith" "Johnson" "Williams" "Jones" "Brown" "Davis" "Miller" "Wilson" "Moore" "Taylor")
countries=("RWA" "PSE" "ETH" "SGP" "TUV" "FRA" "KEN" "IND" "BRA" "CHN")
filePath="./data/user-store.txt"

generate_random_date() {
    year=$((RANDOM % 22 + 2000)) # Random year between 2000 and 2021
    month=$((RANDOM % 12 + 1))   # Random month between 1 and 12
    day=$((RANDOM % 28 + 1))     # Random day between 1 and 28 (to avoid invalid dates)
    printf "%04d-%02d-%02d" "$year" "$month" "$day"
}

hashing() {
    echo -n "$1" | sha256sum | awk '{print $1}'
}

generate_later_date() {
  start_date="$1"
  start_year=$(date -d "$start_date" +%Y)
  start_month=$(date -d "$start_date" +%m)
  start_day=$(date -d "$start_date" +%d)

# #   year=$((RANDOM % (2023 - (start_year + 1) + 1) + (start_year + 1))) 
  year=$((RANDOM % (2022 - $start_year) + $start_year)) 
  month=$((RANDOM % 12 + 1))  
  day=$((RANDOM % 28 + 1))  
  
  echo "$year-$month-$day"
# echo $start_date
}

generate_later_date2() {
  start_date="$1"
  start_year=$(date -d "$start_date" +%Y)
  start_month=$(date -d "$start_date" +%m)
  start_day=$(date -d "$start_date" +%d)

# #   year=$((RANDOM % (2023 - (start_year + 1) + 1) + (start_year + 1))) 
  year=$((RANDOM % (2024 - $start_year) + $start_year)) 
  month=$((RANDOM % 12 + 1))  
  day=$((RANDOM % 28 + 1))  
  
  echo "$year-$month-$day"
}

generate_boolean() {
  bool_value=$((RANDOM % 2))
  if [[ $bool_value -eq 1 ]]; then
    echo "true"
  else
    echo "false"
  fi
}

# Loop 500 times
for i in {1..100}
do
    uuid=$(uuidgen)
    email="patient$i@example.com"
    first_name=${FIRST_NAMES[RANDOM % ${#FIRST_NAMES[@]}]}
    last_name=${LAST_NAMES[RANDOM % ${#LAST_NAMES[@]}]}
    dob=$(generate_random_date)
    hashedPassword=$(hashing "password123")
    
    hiv_status=$(generate_boolean)
    diagnosis_date=""
    if [[ $hiv_status == "true" ]]; then
        diagnosis_date=$(generate_later_date "$dob")
        echo $diagnosis_date
    fi

    # Generate ART status
    art_status="false"
    if [[ $hiv_status == "true" ]]; then
        art_status=$(generate_boolean)
    fi
    art_start_date=""
    if [[ $art_status == "true" && $hiv_status == "true" ]]; then
        art_start_date=$(generate_later_date2 "$diagnosis_date")
    fi
    country=${countries[$RANDOM % ${#countries[@]}]}

    min=-5
    max=60
    life_expectancy=$((RANDOM % (max - min + 1) + min))
    userInfo="$email,$uuid,"Patient",$hashedPassword,$first_name,$last_name,$dob,$hiv_status,$diagnosis_date,$art_status,$art_start_date,$country,$life_expectancy"
    echo -e "$userInfo" >> "$filePath"
    
    # Example action: Sleeping for 1 second (you can replace this with your own command)
    # sleep 1
done

echo "Completed 100 iterations."
