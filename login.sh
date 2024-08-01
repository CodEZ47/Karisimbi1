#!/bin/bash

USER_STORE="user-store.txt"

# Function to handle user login
login_user() {
  echo "Enter email: "
  read email
  echo "Enter password: "
  read -s password
  hashed_password=$(echo -n "$password" | openssl dgst -sha256 | awk '{print $2}')

  # Check if user exists
  user_record=$(grep "$email" $USER_STORE)
  if [[ $user_record == *"$hashed_password"* ]]; then
    echo "Login successful!"
    echo $user_record
  else
    echo "Invalid email or password."
  fi
}

login_user
