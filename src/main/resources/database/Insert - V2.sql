USE ShopShoesJN
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
INSERT INTO [Customer] ([Code], [UserName], [Password], [FullName], [Gender], [Email], [Phone], [BirthDate], [ImageURL], [Status], [CreatedAt], [UpdatedAt]) VALUES
('CUS001', 'customer1', 'password123', 'Nguyễn Văn A', 'Nam', 'a@gmail.com', '0123456789', '1990-01-01', NULL, 1, GETDATE(), GETDATE()),
('CUS002', 'customer2', 'password123', 'Trần Thị B', 'Nữ', 'b@gmail.com', '0123456788', '1992-02-02', NULL, 1, GETDATE(), GETDATE()),
('CUS003', 'customer3', 'password123', 'Lê Văn C', 'Nam', 'c@gmail.com', '0123456787', '1988-03-03', NULL, 1, GETDATE(), GETDATE());

-- Thêm dữ liệu cho bảng Employee
INSERT INTO [Employee] ([Code], [UserName], [Password], [FullName], [Gender], [BirthDate], [Phone], [Email], [Address], [ImageURL], [RoleId], [Status], [Note], [CreatedAt], [UpdatedAt]) VALUES
('EMP001', 'employee1', 'password123', 'Lê Văn C', 1, '1985-03-03', '0123456787', 'c@gmail.com', 'Hà Nội', NULL, 1, 1, NULL, GETDATE(), GETDATE()),
('EMP002', 'employee2', 'password123', 'Phạm Thị D', 0, '1987-04-04', '0123456786', 'd@gmail.com', 'Hồ Chí Minh', NULL, 1, 1, NULL, GETDATE(), GETDATE());

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
INSERT INTO [OrderPayment] ([MethodName], [PaymentDate], [Amount], [Status], [Note]) VALUES
('Thẻ tín dụng', GETDATE(), 1520000, 1, 'Thanh toán đơn hàng ORD001'),
('Tiền mặt', GETDATE(), 2025000, 1, 'Thanh toán đơn hàng ORD002');

-- Thêm dữ liệu cho bảng ProductImage
INSERT INTO [ProductImage] ([ImageURL], [ProductId], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('http://example.com/image1.jpg', 1, GETDATE(), GETDATE(), 1, 1),
('http://example.com/image2.jpg', 2, GETDATE(), GETDATE(), 1, 1),
('http://example.com/image3.jpg', 3, GETDATE(), GETDATE(), 1, 1);

-- Thêm dữ liệu cho bảng ProductDetail
INSERT INTO [ProductDetail] ([Name], [Quantity], [Price], [Weight], [ProductId], [SizeId], [ColorId], [PromotionId], [Status], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('Giày Thể Thao Nike - M', 100, 1500000, 500, 1, 2, 1, NULL, 1, GETDATE(), GETDATE(), 1, 1),
('Giày Công Sở Adidas - L', 50, 2000000, 600, 2, 3, 2, 1, 1, GETDATE(), GETDATE(), 1, 1),
('Giày Du Lịch Puma - XL', 70, 1200000, 550, 3, 4, 3, NULL, 1, GETDATE(), GETDATE(), 1, 1);

-- Thêm dữ liệu cho bảng Orders
INSERT INTO [Orders] ([Code], [UserId], [EmployeeId], [VoucherId], [OrderPaymentId], [OrderStatus], [ShippingStatus], [PaymentDate], [Subtotal], [ShippingCost], [Total], [OrderType], [TrackingNumber], [Notes], [CreatedAt], [UpdatedAt], [CreatedBy], [UpdatedBy]) VALUES
('ORD001', 1, 1, NULL, 1, 1, 1, GETDATE(), 1500000, 20000, 1520000, 'Online', 'TRACK123', 'Ghi chú đơn hàng', GETDATE(), GETDATE(), 1, 1),
('ORD002', 2, 2, NULL, 1, 1, 1, GETDATE(), 2000000, 25000, 2025000, 'Offline', 'TRACK124', 'Ghi chú đơn hàng 2', GETDATE(), GETDATE(), 1, 1);

-- Thêm dữ liệu cho bảng OrderDetail
INSERT INTO [OrderDetail] ([OrderId], [ProductDetailId], [Quantity], [Price]) VALUES
(1, 1, 1, 1500000),
(1, 2, 1, 2000000),
(2, 3, 1, 1200000);

-- Thêm dữ liệu cho bảng Cart
INSERT INTO [Cart] ([UserId]) VALUES
(1),
(2);

-- Thêm dữ liệu cho bảng CartDetail
INSERT INTO [CartDetail] ([CartId], [ProductDetailId], [Quantity]) VALUES
(1, 1, 2),
(1, 2, 1),
(2, 3, 1);

-- Thêm dữ liệu cho bảng Voucher
INSERT INTO [Voucher] ([Code], [Description], [StartDate], [EndDate], [DiscountValue], [VoucherType], [MinimumOrderValue], [MaximumDiscountAmount], [Quantity], [Status], [CreatedAt], [UpdatedAt]) VALUES
('VOUCHER1', 'Giảm giá 10%', GETDATE(), DATEADD(MONTH, 1, GETDATE()), 100000, 'Giảm Giá Theo Đơn Hàng', 500000, 1000000, 50, 1, GETDATE(), GETDATE()),
('VOUCHER2', 'Giảm giá 20%', GETDATE(), DATEADD(MONTH, 1, GETDATE()), 200000, 'Giảm Giá Theo Đơn Hàng', 1000000, 2000000, 30, 1, GETDATE(), GETDATE());


SELECT * FROM CartDetail