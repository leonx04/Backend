-- Insert data into Employee table
INSERT INTO [Employee] ([Code], [UserName], [Password], [FullName], [Gender], [BirthDate], [Phone], [Email], [Address], [URL], [RoleId], [Status], [Note])
VALUES
    ('EMP001', 'john_doe', 'password123', 'John Doe', 1, '1985-01-15', '123456789', 'john.doe@example.com', '123 Main St, City', 'http://example.com/john', 1, 1, 'Note for John'),
    ('EMP002', 'jane_smith', 'password123', 'Jane Smith', 2, '1990-02-20', '987654321', 'jane.smith@example.com', '456 Market St, City', 'http://example.com/jane', 2, 1, 'Note for Jane');

-- Insert data into Customer table
INSERT INTO [Customer] ([Code], [UserName], [Password], [FullName], [Gender], [Email], [Phone], [BirthDate], [URL], [Status])
VALUES
    ('CUS001', 'alice_wong', 'password123', 'Alice Wong', 'Female', 'alice.wong@example.com', '555666777', '1992-04-25', 'http://example.com/alice', 1),
    ('CUS002', 'bob_martin', 'password123', 'Bob Martin', 'Male', 'bob.martin@example.com', '444555666', '1988-03-30', 'http://example.com/bob', 1);

-- Insert data into Address table
INSERT INTO [Address] ([CustomerId], [DetailAddress], [City], [District], [Commune], [Status])
VALUES
    (1, '789 Oak St, City', 'City', 'District 1', 'Commune A', 1),
    (2, '101 Pine St, City', 'City', 'District 2', 'Commune B', 1);

-- Insert data into Product table
INSERT INTO [Product] ([Name], [Description], [CategoryId], [BrandId], [MaterialId], [SoleId], [Status], [CreatedBy], [UpdatedBy])
VALUES
    ('Product A', 'Description for Product A', 1, 1, 1, 1, 1, 1, 1),
    ('Product B', 'Description for Product B', 2, 2, 2, 2, 1, 1, 1);

-- Insert data into ProductImage table
INSERT INTO [ProductImage] ([ImageUrl], [ProductId], [CreatedBy], [UpdatedBy])
VALUES
    ('http://example.com/imageA.jpg', 1, 1, 1),
    ('http://example.com/imageB.jpg', 2, 1, 1);

-- Insert data into ProductDetail table
INSERT INTO [ProductDetail] ([Code], [Quantity], [Price], [Weight], [ProductId], [SizeId], [ColorId], [PromotionId], [Status], [CreatedBy], [UpdatedBy])
VALUES
    ('PD001', 100, 500.00, 200, 1, 1, 1, NULL, 1, 1, 1),
    ('PD002', 200, 1000.00, 250, 2, 2, 2, NULL, 1, 1, 1);

-- Insert data into Order table
INSERT INTO [Order] ([Code], [UserId], [EmployeeId], [VoucherId], [OrderPayMentId], [RecipientName], [RecipientPhone], [TransactionCode], [OrderStatus], [ShippingStatus], [PaymentDate], [Subtotal], [ShippingCost], [Total], [OrderType], [TrackingNumber], [Notes], [CreatedBy], [UpdatedBy])
VALUES
    ('ORD001', 1, 1, NULL, NULL, 'Alice Wong', '555666777', 'TRANS001', 1, 1, '2023-09-15', 1000.00, 50.00, 1050.00, 'Online', 'TRACK001', 'Note for order 1', 1, 1),
    ('ORD002', 2, 2, NULL, NULL, 'Bob Martin', '444555666', 'TRANS002', 1, 1, '2023-09-16', 2000.00, 75.00, 2075.00, 'In-store', 'TRACK002', 'Note for order 2', 1, 1);

-- Insert data into Payment table
INSERT INTO [Payment] ([PaymentMethod])
VALUES
    ('Credit Card'),
    ('Paypal');

-- Insert data into OrderPayment table
INSERT INTO [OrderPayment] ([OrderId], [PaymentId], [Transaction], [PaymentDate], [Amount], [Status], [Note])
VALUES
    (1, 1, 'TRANS001', '2023-09-15', 1050.00, 1, 'Paid via Credit Card'),
    (2, 2, 'TRANS002', '2023-09-16', 2075.00, 1, 'Paid via Paypal');

-- Insert data into OrderDetail table
INSERT INTO [OrderDetail] ([OrderId], [ProductDetailId], [Quantity], [Price])
VALUES
    (1, 1, 2, 500.00),
    (2, 2, 1, 1000.00);

-- Insert data into Cart table
INSERT INTO [Cart] ([UserId])
VALUES
    (1),
    (2);

-- Insert data into CartDetail table
INSERT INTO [CartDetail] ([CartId], [ProductDetailId], [Quantity])
VALUES
    (1, 1, 1),
    (2, 2, 2);

-- Insert data into Promotion table
INSERT INTO [Promotion] ([Code], [Name], [StartDate], [EndDate], [Quantity], [DiscountPercentage], [Status], [CreatedBy], [UpdatedBy])
VALUES
    ('PROMO10', '10% Discount', '2023-09-01', '2023-09-30', 100, 10.00, 1, 1, 1),
    ('PROMO20', '20% Discount', '2023-09-15', '2023-10-15', 50, 20.00, 1, 1, 1);

-- Insert data into Voucher table
INSERT INTO [Voucher] ([Code], [Name], [StartDate], [EndDate], [DiscountAmount], [DiscountPercentage], [VoucherType], [MinimumDiscountAmount], [MaximumDiscountAmount], [Quantity], [Status])
VALUES
    ('VOUCHER10', 'Voucher 10%', '2023-09-01', '2023-09-30', NULL, 10.00, 'Percentage', 100.00, 500.00, 100, 1),
    ('VOUCHER20', 'Voucher 20%', '2023-09-15', '2023-10-15', NULL, 20.00, 'Percentage', 200.00, 1000.00, 50, 1);

-- Insert data into CustomerVoucher table
INSERT INTO [CustomerVoucher] ([CustomerId], [VoucherId], [UsageCount], [IsSaved])
VALUES
    (1, 1, 0, 1),
    (2, 2, 1, 0);

-- Insert data into Size table
INSERT INTO [Size] ([Code], [Name], [Status], [CreatedBy], [UpdatedBy])
VALUES
    ('SIZE01', 'Small', 1, 1, 1),
    ('SIZE02', 'Medium', 1, 1, 1);

-- Insert data into Color table
INSERT INTO [Color] ([Code], [Name], [Status], [CreatedBy], [UpdatedBy])
VALUES
    ('COLOR01', 'Red', 1, 1, 1),
    ('COLOR02', 'Blue', 1, 1, 1);

-- Insert data into Category table
INSERT INTO [Category] ([Code], [Name], [Status], [CreatedBy], [UpdatedBy])
VALUES
    ('CAT01', 'Electronics', 1, 1, 1),
    ('CAT02', 'Furniture', 1, 1, 1);

-- Insert data into Brand table
INSERT INTO [Brand] ([Code], [Name], [Status], [CreatedBy], [UpdatedBy])
VALUES
    ('BRAND01', 'Brand A', 1, 1, 1),
    ('BRAND02', 'Brand B', 1, 1, 1);

-- Insert data into Material table
INSERT INTO [Material] ([Code], [Name], [Status], [CreatedBy], [UpdatedBy])
VALUES
    ('MAT01', 'Plastic', 1, 1, 1),
    ('MAT02', 'Metal', 1, 1, 1);

-- Insert data into Sole table
INSERT INTO [Sole] ([Code], [Name], [Status], [CreatedBy], [UpdatedBy])
VALUES
    ('SOLE01', 'Rubber', 1, 1, 1),
    ('SOLE02', 'Leather', 1, 1, 1);
