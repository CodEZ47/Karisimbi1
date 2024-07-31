#!/bin/bash

# Function to generate a UUID
generate_uuid() {
  uuid=$(uuidgen)
  echo $uuid
}

# Prompt the user for an email
read -p "Enter your email: " email

# Generate a UUID
uuid=$(generate_uuid)

# Save the email and UUID to user.txt
echo "Email: $email, UUID: $uuid" >> user.txt

# Display the UUID to the user
echo "Your UUID is: $uuid"

# End the first part of the script
exit 0
