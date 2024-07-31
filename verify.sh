#!/bin/bash

# Function to verify email and UUID
verify_user() {
  while read -r line; do
    stored_email=$(echo $line | cut -d',' -f1 | cut -d':' -f2 | tr -d ' ')
    stored_uuid=$(echo $line | cut -d',' -f2 | cut -d':' -f2 | tr -d ' ')
    if [[ "$stored_email" == "$email" && "$stored_uuid" == "$uuid" ]]; then
      return 0
    fi
  done < user.txt
  return 1
}

# Function to hash a password using openssl
hash_password() {
  echo -n "$1" | openssl dgst -sha256 | awk '{print $2}'
}

# Prompt for email and UUID
read -p "Enter your email: " email
read -p "Enter your UUID: " uuid

# Verify the email and UUID
if verify_user; then
  echo "Email and UUID verified. Proceeding with registration..."

  # Prompt for additional registration details
  read -p "Enter your first name: " first_name
  read -p "Enter your last name: " last_name
  read -p "Enter your date of birth (YYYY-MM-DD): " dob

  # Prompt for password and confirm password
  while true; do
    read -s -p "Enter your password: " password
    echo
    read -s -p "Confirm your password: " confirm_password
    echo
    if [[ "$password" == "$confirm_password" ]]; then
      hashed_password=$(hash_password "$password")
      break
    else
      echo "Passwords do not match. Please try again."
    fi
  done

  # Save the details to pat.txt
  echo "Email: $email, First Name: $first_name, Last Name: $last_name, DOB: $dob, Password: $hashed_password" >> pat.txt

  # Print a confirmation message
  echo "The registration details have been saved to pat.txt"
else
  echo "Error: Email and UUID do not match any entry in user.txt"
fi
