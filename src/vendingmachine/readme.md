# Vending Machine - Low-Level Design (LLD)

### Features
- **Multiple Products**: The vending machine should support multiple products with varying prices and quantities.  
  *Product Model*: `Product (name, price, quantity)`

- **Payment Methods**: The machine should accept coins and notes with different denominations.  
  *Payment Types*: `COIN`, `NOTES`

- **Product Dispensation**: The machine should dispense the selected product and return change if necessary.

- **Inventory Management**: The machine should keep track of available products and their quantities.  
  *Inventory*: `List of Products`

- **Concurrency & Data Consistency**: The machine should handle multiple transactions concurrently while ensuring data consistency.  
  *Thread-Safe* operations are required.

- **Restocking & Money Collection**: The machine should provide an interface for restocking products and collecting money.  
  *Machine State*: `Idle`, `Ready`, `Dispense`, `Maintenance`

- **Exception Handling**: The machine should handle exceptional scenarios such as:
    - Insufficient funds
    - Out-of-stock products
