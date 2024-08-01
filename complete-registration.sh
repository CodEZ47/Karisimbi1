#!/bin/bash

USER_STORE="user-store.txt"

# Function to hash a password using openssl
hash_password() {
  echo -n "$1" | openssl dgst -sha256 | awk '{print $2}'
}

# Function to verify email and UUID
verify_user() {
  local email="$1"
  local uuid="$2"
  while IFS=',' read -r stored_uuid role stored_email; do
    stored_email=$(echo $stored_email | tr -d ' ')
    if [[ "$stored_email" == "$email" && "$stored_uuid" == "$uuid" ]]; then
      return 0
    fi
  done < "$USER_STORE"
  return 1
}

# Function to complete patient registration
complete_patient_registration() {
  echo "Enter your email: "
  read email
  echo "Enter your UUID: "
  read uuid

  # Verify the email and UUID
  if verify_user "$email" "$uuid"; then
    echo "Email and UUID verified. Proceeding with registration..."
  else
    echo "Error: Email and UUID do not match any entry in $USER_STORE"
    exit 1
  fi

  echo "Enter first name: "
  read first_name
  echo "Enter last name: "
  read last_name
  echo "Enter date of birth (YYYY-MM-DD): "
  read dob
  echo "Do you have HIV? (yes/no): "
  read hiv_status
  diagnosis_date=""
  on_art=""
  art_start_date=""
  if [ "$hiv_status" == "yes" ]; then
    echo "Enter diagnosis date (YYYY-MM-DD): "
    read diagnosis_date
    echo "Are you on ART drugs? (yes/no): "
    read on_art
    if [ "$on_art" == "yes" ]; then
      echo "Enter ART start date (YYYY-MM-DD): "
      read art_start_date
    fi
  fi
  echo "Enter country of residence (ISO code): "
  read country
  while true; do
    echo "Enter password: "
    read -s password
    echo
    echo "Confirm password: "
    read -s confirm_password
    echo
    if [[ "$password" == "$confirm_password" ]]; then
      hashed_password=$(hash_password "$password")
      break
    else
      echo "Passwords do not match. Please try again."
    fi
  done

  # Update user-store.txt with patient details
  new_record="$uuid, PATIENT, $email, $first_name, $last_name, $dob, $hiv_status, $diagnosis_date, $on_art, $art_start_date, $country, $hashed_password"
  sed -i "s/^$uuid,.*/$new_record/" $USER_STORE
  echo "Patient registration completed."
}

complete_patient_registration
