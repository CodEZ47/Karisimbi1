#!/bin/bash

# set the admin's credentials
admin_first_name='Ikael'
admin_last_name='Kali'
admin_email='1@q.com'

admin_password=$(echo -n '123' | openssl dgst -sha256 | awk '{print $2}')

# Create the user-store.txt file with the initial admin user
echo "$admin_email, ADMIN, $admin_first_name, $admin_last_name, $admin_password" > user-store.txt
