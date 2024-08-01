.#!/bin/bash

# Define the scripts paths
initial_admin_script="initialAdmin.sh"
initiate_reg_script="initiate-registration.sh"
complete_reg_script="complete-registration.sh"
patient_login_script="login.sh"


# Main menu
echo '     Welcome to Life Prognosis Management'
echo '-----------------------------------------------'
echo "1. Initiate Patient Registration"
echo "2. Complete Patient Registration"
echo "3. Login"
echo "4. Download CSV files"
echo "Enter your choice: "
read choice

case $choice in

  1)
    bash $initiate_reg_script
    ;;
  2)
    bash $complete_reg_script
    ;;
  3)
    bash $patient_login_script
    ;;
  4)
    javac AdminDownload.java
    java AdminDownload
    ;;
  *)
    echo "Invalid choice"
    ;;
esac
