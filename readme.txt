GROUP EB01
-----------------------------------------------------
A0111860R Ho Ren Sen (Engineer/Leader)
A0105484H Jin Wenqian (Designer)
A0119323M Nguyen Tran Nhat Uyen (Designer)
A0124493E Zhang Zhixuan (Engineer)


VIEWING INSTRUCTIONS
-----------------------------------------------------
URL: 



NOTE TO SELF
-----------------------------------------------------
Rename "LaundryButler" to "src". Then upload to IVLE.



FUNCTION CHECKLIST
-----------------------------------------------------

CUSTOMER USE CASES

- Sign up an account with no referring customer ID.
- Sign up an account with referrer's customer ID.
- Award referrer and referent one free dry cleaning if a valid referrer's customer ID is entered during signup.
- Randomly generate verification code upon customer registration (set account status to Pending).
- Email verification code to customer.
- Enter verification code if manual signup (set account status to Verified and clear verification code).
- Sign up with Facebook with an optional referring customer ID.
- Login with LaundryButler account.
- Login with Facebook account.
- Link Facebook and LaundryButler account.
- Add Airbnb username in LaundryButler profile.
- Logout.
- View account information i.e. number of dry cleaning, number of express cleaning, number of unscheduled boxes
- Update account information i.e. first name, last name, mobile number
- Change password with confirm password.

- View addresses.
- Create address.
- Update address.
- Delete address.
- Mark an address as Airbnb address if user logs in to LaundryButler from Airbnb.

- Pay with Stripe.
- Record transaction and transaction line items.
- Update number of dry cleaning.
- Update number of express cleaning.
- Immediately generate boxes according to customers' purchased plan.
- Immediately generate weekly pickup dates and make it "Scheduled".
- Randomly generate 3-digit box passcode for each box.

- Add products to shopping cart.
- Edit quantity of products on shopping cart.
- Remove items on shopping cart.
- View a list of items in shopping cart with total price.
- Checkout shopping cart, with source of box as "Purchased".
- Checkout shopping cart, as a gift for someone else. Giver pays and incur a transaction with no boxes. The beneficiary gets the boxes with the source as "Gifted by (name)".

- View list of boxes.
- View details of a box.
- Share a box with neighbours.
- Allow or deny neighbour's request to share in.
- Rate a completed laundry box (only if delivered).
- Share a completed box on Facebook as a referral.

- View transaction history.
- View transaction details.

- Send email to admins.



EMPLOYEE/ADMIN USE CASES

GENERAL
- Employee login.

PRODUCTS
- View list of all products (boxes and add-ons).
- View product details.
- Add product. (ADMIN ONLY)
- Edit product (only if it has never been purchased before). (ADMIN ONLY)
- Delete product (only if it has never been purchased before). (ADMIN ONLY)

EMPLOYEES
- View all employees.
- Add employee. (ADMIN ONLY)
- Update employee (only for itself). (ADMIN CAN UPDATE ANYONE)
- Delete employee (only if he/she has never been assigned a task before). (ADMIN ONLY)

CUSTOMERS
- Send email to customers. (ADMIN ONLY)
- View list of all customers.
- View customer details.
- Update customer. (ADMIN ONLY)
- Suspend customer (change account status from Verified to Suspended). (ADMIN ONLY)

TRANSACTIONS
- View transaction history for all customers. (ADMIN ONLY)
- View transaction details. (ADMIN ONLY)

BOXES
- Assign employees to boxes (randomly). (ADMIN ONLY)
- View all boxes.
- View a map of boxes based on customer addresses.
- View box details.
- Mark box as "Picked Up", "Cleaned", "Delivered".
