#!/bin/bash

# Function to hash a password using openssl
hash_password() {
  echo -n "$1" | openssl dgst -sha256 | awk '{print $2}'
}

# Function to verify login
verify_login() {
  hashed_input_password=$(hash_password "$password")
  while read -r line; do
    stored_email=$(echo $line | cut -d',' -f1 | cut -d':' -f2 | tr -d ' ')
    stored_password=$(echo $line | cut -d',' -f5 | cut -d':' -f2 | tr -d ' ')
    if [[ "$stored_email" == "$email" && "$stored_password" == "$hashed_input_password" ]]; then
      return 0
    fi
  done < pat.txt
  return 1
}

# Function to display the profile
display_profile() {
  while read -r line; do
    stored_email=$(echo $line | cut -d',' -f1 | cut -d':' -f2 | tr -d ' ')
    if [[ "$stored_email" == "$email" ]]; then
      echo "Profile:"
      echo "$line" | awk -F, '{print "First Name:" $3, "Last Name:" $4, "DOB:" $5}'
      break
    fi
  done < pat.txt
}

# Function to handle the main menu
main_menu() {
  while true; do
    echo "Main Menu:"
    echo "1. View Profile"
    echo "2. Logout"
    read -p "Choose an option: " choice
    case $choice in
      1)
        display_profile
        ;;
      2)
        echo "Logging out..."
        break
        ;;
      *)
        echo "Invalid option. Please try again."
        ;;
    esac
    echo "Press any key to go back to the main menu..."
    read -n 1
  done
}

# Prompt for login details
read -p "Enter your email: " email
read -s -p "Enter your password: " password
echo

# Verify login
if verify_login; then
  echo "Login successful."
  main_menu
else
  echo "Login failed. Incorrect email or password."
fi
