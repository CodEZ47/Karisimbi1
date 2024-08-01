#!/bin/bash

USER_STORE="user-store.txt"

# Function to generate UUID
generate_uuid() {
  uuid=$(cat /proc/sys/kernel/random/uuid)
  echo $uuid
}

# Verify admin credentials
echo "Enter admin email: "
read admin_email
echo "Enter admin password: "
read -s admin_password
hashed_password=$(echo -n "$admin_password" | openssl dgst -sha256 | awk '{print $2}')

admin_record=$(grep "$admin_email, ADMIN," $USER_STORE)
if [[ $admin_record == *"$hashed_password"* ]]; then
  echo "Admin verified."

  # Initiate patient registration
  echo "Enter patient email: "
  read patient_email
  uuid=$(generate_uuid)
  echo "$uuid, PATIENT, $patient_email" >> $USER_STORE
  echo "Patient registration initiated with UUID: $uuid"
else
  echo "Invalid admin credentials. GO BACK!!!"
fi
