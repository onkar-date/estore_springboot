import mysql.connector
import json
import os

# Database connection
cnx = mysql.connector.connect(
    user='root',
    password='root',
    host='db',  # Access host's database
    port=3306,  # Adjust this if necessary
    database='estore_db'
)

cursor = cnx.cursor()

# Load JSON data
json_file_path = os.path.join('data_seed', 'products.json')
with open(json_file_path, 'r', encoding='utf-8') as file:
    data = json.load(file)

# Seed data into the database
for product in data['products']:
    product_name = product['name']
    product_description = product['description']
    price = product['price']
    stock_quantity = product['stock_quantity']
    seller_id = product['seller_id']

    # Image file path
    image_path = os.path.join('data_seed/images', f"{product_name}.webp")

    try:
        with open(image_path, 'rb') as img_file:
            image_data = img_file.read()
    except FileNotFoundError:
        print(f"Image file for '{product_name}' not found. Skipping this product.")
        continue

    # Insert into products table
    product_query = """
    INSERT INTO products (name, description, price, stock_quantity, seller_id)
    VALUES (%s, %s, %s, %s, %s)
    """
    product_values = (product_name, product_description, price, stock_quantity, seller_id)

    try:
        cursor.execute(product_query, product_values)
        product_id = cursor.lastrowid
    except mysql.connector.Error as err:
        print(f"Error inserting {product_name}: {err}")
        continue

    # Insert into images table
    image_query = """
    INSERT INTO images (product_id, image, is_primary)
    VALUES (%s, %s, %s)
    """
    image_values = (product_id, image_data, True)

    try:
        cursor.execute(image_query, image_values)
    except mysql.connector.Error as err:
        print(f"Error inserting image for {product_name}: {err}")
        continue

# Commit changes and close connection
cnx.commit()
cursor.close()
cnx.close()
