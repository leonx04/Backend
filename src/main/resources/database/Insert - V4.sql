USE DB_DATN_V4
GO

-- Thêm dữ liệu cho bảng Brand
INSERT INTO [Brand] ([Name], [Status], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('Nike', 1, GETDATE(), GETDATE(), 1, 1),
('Adidas', 1, GETDATE(), GETDATE(), 1, 1),
('Puma', 1, GETDATE(), GETDATE(), 1, 1);

-- Thêm dữ liệu cho bảng Category
INSERT INTO [Category] ([Name], [Status], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('Giày Thể Thao', 1, GETDATE(), GETDATE(), 1, 1),
('Giày Công Sở', 1, GETDATE(), GETDATE(), 1, 1),
('Giày Du Lịch', 1, GETDATE(), GETDATE(), 1, 1);

-- Thêm dữ liệu cho bảng Size
INSERT INTO [Size] ([Name], [Status], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('S', 1, GETDATE(), GETDATE(), 1, 1),
('M', 1, GETDATE(), GETDATE(), 1, 1),
('L', 1, GETDATE(), GETDATE(), 1, 1),
('XL', 1, GETDATE(), GETDATE(), 1, 1);

-- Thêm dữ liệu cho bảng Color
INSERT INTO [Color] ([Name], [Status], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('Đen', 1, GETDATE(), GETDATE(), 1, 1),
('Trắng', 1, GETDATE(), GETDATE(), 1, 1),
('Xanh', 1, GETDATE(), GETDATE(), 1, 1),
('Đỏ', 1, GETDATE(), GETDATE(), 1, 1);

-- Thêm dữ liệu cho bảng Material
INSERT INTO [Material] ([Name], [Status], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('Da', 1, GETDATE(), GETDATE(), 1, 1),
('Vải', 1, GETDATE(), GETDATE(), 1, 1),
('Nỉ', 1, GETDATE(), GETDATE(), 1, 1);

-- Thêm dữ liệu cho bảng Sole
INSERT INTO [Sole] ([Name], [Status], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('Sole Cao Su', 1, GETDATE(), GETDATE(), 1, 1),
('Sole Da', 1, GETDATE(), GETDATE(), 1, 1);

-- Thêm dữ liệu cho bảng Promotion
INSERT INTO [Promotion] ([Code], [Name], [StartDate], [EndDate], [Quantity], [DiscountPercentage], [Status], [CreatedAt], [UpdatedAt]) VALUES
('SUMMER21', 'Khuyến Mãi Mùa Hè', GETDATE(), DATEADD(MONTH, 1, GETDATE()), 100, 15.00, 1, GETDATE(), GETDATE()),
('WINTER21', 'Khuyến Mãi Mùa Đông', GETDATE(), DATEADD(MONTH, 1, GETDATE()), 50, 10.00, 1, GETDATE(), GETDATE());

-- Thêm dữ liệu cho bảng Customer
INSERT INTO [Customer] ([code], [userName], [password], [fullName], [gender], [email], [phone], [birthDate], [imageUrl], [status])
VALUES 
('CUS0000001', 'user1', 'password1', 'Nguyễn Văn A', 1, 'nguyenvana@example.com', '0123456789', '1990-01-01', 'http://example.com/image1.jpg', 1),
('CUS0000002', 'user2', 'password2', 'Trần Thị B', 0, 'tranthib@example.com', '0987654321', '1992-02-02', 'http://example.com/image2.jpg', 1),
('CUS0000003', 'user3', 'password3', 'Lê Văn C', 1, 'levanc@example.com', '0123456789', '1994-03-03', 'http://example.com/image3.jpg', 1),
('CUS0000004', 'user4', 'password4', 'Phạm Thị D', 0, 'phamthid@example.com', '0987654321', '1988-04-04', 'http://example.com/image4.jpg', 1),
('CUS0000005', 'user5', 'password5', 'Nguyễn Văn E', 1, 'nguyenvane@example.com', '0123456789', '1991-05-05', 'http://example.com/image5.jpg', 1);


-- Thêm dữ liệu cho bảng Employee
INSERT INTO [Employee] ([Code], [UserName], [Password], [FullName], [Gender], [BirthDate], [Phone], [Email], [Address], [ImageURL], [RoleId], [Status], [Note], [CreatedAt], [UpdatedAt]) VALUES
('EMP001', 'employee1', 'password123', 'Lê Văn C', 1, '1985-03-03', '0123456787', 'c@gmail.com', 'Hà Nội', NULL, 1, 1, NULL, GETDATE(), GETDATE()),
('EMP002', 'employee2', 'password123', 'Phạm Thị D', 0, '1987-04-04', '0123456786', 'phamdainguyen3@gmail.com', 'Hồ Chí Minh', NULL, 1, 1, NULL, GETDATE(), GETDATE());

-- Thêm dữ liệu cho bảng Address
INSERT INTO [Address] ([CustomerId], [DetailAddress], [RecipientName], [RecipientPhone], [City], [District], [Commune], [Status], [CreatedAt], [UpdatedAt]) VALUES
(1, 'Số 1, Phố ABC, Quận 1', 'Nguyễn Văn A', '0123456789', 'Hà Nội', 'Quận 1', 'Phường 1', 1, GETDATE(), GETDATE()),
(2, 'Số 2, Phố XYZ, Quận 2', 'Trần Thị B', '0123456788', 'Hà Nội', 'Quận 2', 'Phường 2', 1, GETDATE(), GETDATE());

-- Thêm dữ liệu cho bảng Product
INSERT INTO [Product] ([Name], [Description], [CategoryId], [BrandId], [MaterialId], [SoleId], [Status], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('Giày Thể Thao Nike', 'Giày thể thao chất lượng cao', 1, 1, 1, 1, 1, GETDATE(), GETDATE(), 1, 1),
('Giày Công Sở Adidas', 'Giày công sở thanh lịch', 2, 2, 1, 2, 1, GETDATE(), GETDATE(), 1, 1),
('Giày Du Lịch Puma', 'Giày du lịch bền bỉ', 3, 3, 2, 1, 1, GETDATE(), GETDATE(), 1, 1);

-- Thêm dữ liệu cho bảng OrderPayment
-- INSERT INTO [OrderPayment] ([MethodName], [PaymentDate], [Amount], [Status], [Note]) VALUES
-- ('Thẻ tín dụng', GETDATE(), 1520000, 1, 'Thanh toán đơn hàng ORD001'),
-- ('Tiền mặt', GETDATE(), 2025000, 1, 'Thanh toán đơn hàng ORD002');



-- Thêm dữ liệu cho bảng productVariant
INSERT INTO [productVariant] ([SKU], [Quantity], [Price], [Weight], [ProductId], [SizeId], [ColorId], [PromotionId], [Status], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('Giày Thể Thao Nike - M', 100, 1500000, 500, 1, 2, 1, NULL, 1, GETDATE(), GETDATE(), 1, 1),
('Giày Công Sở Adidas - L', 50, 2000000, 600, 2, 3, 2, 1, 1, GETDATE(), GETDATE(), 1, 1),
('Giày Du Lịch Puma - XL', 70, 1200000, 550, 3, 4, 3, NULL, 1, GETDATE(), GETDATE(), 1, 1);

-- Thêm dữ liệu cho bảng ProductImage
INSERT INTO [ProductImage] ([ImageURL], [productVariantId], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('http://example.com/image1.jpg', 1, GETDATE(), GETDATE(), 1, 1),
('http://example.com/image2.jpg', 2, GETDATE(), GETDATE(), 1, 1),
('http://example.com/image3.jpg', 3, GETDATE(), GETDATE(), 1, 1);


-- Thêm dữ liệu cho bảng Voucher
INSERT INTO [Voucher] ([Code], [Description], [StartDate], [EndDate], [DiscountValue], [VoucherType], [MinimumOrderValue], [MaximumDiscountAmount], [Quantity], [Status], [CreatedAt], [UpdatedAt]) VALUES
('VOUCHER1', 'Giảm giá 10%', GETDATE(), DATEADD(MONTH, 1, GETDATE()), 100000, 'Giảm Giá Theo Đơn Hàng', 500000, 1000000, 50, 1, GETDATE(), GETDATE()),
('VOUCHER2', 'Giảm giá 20%', GETDATE(), DATEADD(MONTH, 1, GETDATE()), 200000, 'Giảm Giá Theo Đơn Hàng', 1000000, 2000000, 30, 1, GETDATE(), GETDATE());


-- Thêm dữ liệu cho bảng Orders
INSERT INTO Orders (Code, OrdersAddress, RecipientName, RecipientPhone, VoucherId, CustomerId, OrderPayment, OrderStatus, ShippingStatus, PaymentDate, Subtotal, ShippingCost, Total, OrderType)
VALUES
('ORD001', N'123 Đường ABC, Hà Nội', N'Khách hàng 1', '0123456789', 1, 1, 1, 4, 2, '2024-01-15', 2000000, 30000, 2030000, N'Online'),
('ORD002', N'456 Đường XYZ, Hồ Chí Minh', N'Khách hàng 2', '0987654321', 2, 2, 1, 4, 2, '2024-01-16', 3600000, 30000, 3630000, N'Online'),
('ORD003', N'789 Đường DEF, Đà Nẵng', N'Khách hàng 3', '0369852147', 1, 3, 1, 4, 2, '2024-01-17', 1500000, 30000, 1530000, N'Online'),
('ORD004', N'321 Đường GHI, Hà Nội', N'Khách hàng 1', '0123456789', NULL, 1, 1, 4, 2, '2024-01-18', 2000000, 30000, 2030000, N'Offline'),
('ORD005', N'654 Đường JKL, Hồ Chí Minh', N'Khách hàng 2', '0987654321', 2, 2, 1, 3, 1, '2024-01-19', 1800000, 30000, 1830000, N'Online'),
('ORD006', N'987 Đường MNO, Đà Nẵng', N'Khách hàng 3', '0369852147', NULL, 3, 1, 3, 1, '2024-01-20', 4500000, 0, 4500000, N'Offline'),
('ORD007', N'147 Đường PQR, Hà Nội', N'Khách hàng 1', '0123456789', 1, 1, 2, 2, 1, '2024-01-21', 3000000, 30000, 3030000, N'Online'),
('ORD008', N'258 Đường STU, Hồ Chí Minh', N'Khách hàng 2', '0987654321', NULL, 2, 2, 2, 1, '2024-01-22', 1500000, 30000, 1530000, N'Online'),
('ORD009', N'369 Đường VWX, Đà Nẵng', N'Khách hàng 3', '0369852147', 2, 3, 1, 1, 0, NULL, 2000000, 30000, 2030000, N'Online'),
('ORD010', N'741 Đường YZ, Hà Nội', N'Khách hàng 1', '0123456789', NULL, 1, 2, 1, 0, NULL, 1800000, 30000, 1830000, N'Online'),
('ORD013', N'159 Đường EF, Hà Nội', N'Khách hàng 1', '0123456789', 2, 1, 2, 5, 2, '2024-01-25', 1500000, 30000, 1530000, N'Online'),
('ORD014', N'357 Đường GH, Hồ Chí Minh', N'Khách hàng 2', '0987654321', NULL, 2, 1, 5, 2, '2024-01-26', 2000000, 30000, 2030000, N'Online'),
('ORD015', N'951 Đường IJ, Đà Nẵng', N'Khách hàng 3', '0369852147', 1, 3, 1, 5, 2, '2024-01-27', 1800000, 30000, 1830000, N'Online');

-- Thêm dữ liệu cho bảng OrderDetail
INSERT INTO OrderDetail (OrderId, productVariantId, Quantity, Price)
VALUES
-- ORD001: Single product order
(2, 1, 1, 2000000),  

-- ORD002: Multiple products
(2, 2, 2, 1800000),  
(2, 3, 1, 1500000),  

-- ORD003: Single product order
(3, 1, 1, 1500000),  

-- ORD004: Single product order with higher quantity
(4, 2, 2, 2000000),  

-- ORD005: Single product order
(5, 3, 1, 1800000),  

-- ORD006: Multiple products with high quantity (Offline order, no shipping cost)
(6, 1, 2, 1500000),
(6, 2, 1, 1500000),  

-- ORD007: Multiple products
(7, 3, 1, 1500000),
(7, 1, 1, 1500000),  

-- ORD008: Single product order	
(8, 2, 1, 1500000),  

-- ORD009: Single product (Pending order)
(9, 3, 1, 2000000),  

-- ORD010: Single product (Pending order)
(10, 1, 1, 1800000), 

-- ORD013: Single product (Completed with return/refund)
(11, 2, 1, 1500000),

-- ORD014: Multiple products (Completed with return/refund)
(12, 3, 1, 1000000),
(12, 1, 1, 1000000), 

-- ORD015: Single product (Completed with return/refund)
(13, 2, 1, 1800000);

INSERT INTO OrderStatusLog (EmployeeId, Orderid, OrderStatus, UpdatedAt)
VALUES
-- Order 1 complete lifecycle
(1, 2, 5, '2024-01-15 08:00:00'), 
(2, 3, 1, '2024-01-15 09:00:00'), 
(2, 3, 2, '2024-01-15 10:00:00'), 
(1, 2, 3, '2024-01-15 14:00:00'), 
(1, 2, 4, '2024-01-16 10:00:00'), 

-- Order 2 complete lifecycle
(1, 2, 5, '2024-01-16 09:00:00'),
(2, 2, 1, '2024-01-16 10:00:00'),
(2, 2, 2, '2024-01-16 11:00:00'),
(2, 2, 3, '2024-01-16 15:00:00'),
(1, 2, 4, '2024-01-17 11:00:00'),

-- Order 3 complete lifecycle
(1, 3, 5, '2024-01-17 10:00:00'),
(2, 3, 1, '2024-01-17 11:00:00'),
(2, 3, 2, '2024-01-17 12:00:00'),
(1, 3, 3, '2024-01-17 16:00:00'),
(1, 3, 4, '2024-01-18 12:00:00');

-- Thêm dữ liệu cho bảng Cart
INSERT INTO [Cart] ([UserId]) VALUES
(1),
(2);

-- Thêm dữ liệu cho bảng CartDetail
INSERT INTO [CartDetail] ([CartId], [productVariantId], [Quantity]) VALUES
(1, 1, 2),
(1, 2, 1),
(2, 3, 1);


select * from Product
select * from ProductVariant
select * from Brand
