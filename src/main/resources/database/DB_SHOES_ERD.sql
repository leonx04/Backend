CREATE DATABASE ShopShoesJN
GO

USE ShopShoesJN
GO

CREATE TABLE [Employee] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] NVARCHAR(255) UNIQUE NOT NULL,
  [UserName] NVARCHAR(100) UNIQUE NOT NULL,
  [Password] NVARCHAR(255) NOT NULL,
  [FullName] NVARCHAR(255),
  [Gender] INT,
  [BirthDate] DATE,
  [Phone] NVARCHAR(20),
  [Email] NVARCHAR(255),
  [Address] NVARCHAR(255),
  [URL] NVARCHAR(255),
  [RoleId] INT DEFAULT (2),
  [Status] INT DEFAULT (1),
  [Note] NVARCHAR(MAX),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE())
)
GO

CREATE TABLE [Customer] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] NVARCHAR(255) UNIQUE NOT NULL,
  [UserName] NVARCHAR(100) UNIQUE NOT NULL,
  [Password] NVARCHAR(255) NOT NULL,
  [FullName] NVARCHAR(255),
  [Gender] NVARCHAR(50),
  [Email] NVARCHAR(255) NOT NULL,
  [Phone] VARCHAR(20),
  [BirthDate] DATE,
  [URL] NVARCHAR(255),
  [Status] INT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE())
)
GO

CREATE TABLE [Address] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [CustomerId] INT NOT NULL,
  [DetailAddress] NVARCHAR(255) NOT NULL,
  [City] NVARCHAR(255) NOT NULL,
  [District] NVARCHAR(255) NOT NULL,
  [Commune] NVARCHAR(255) NOT NULL,
  [Status] INT NOT NULL,
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE())
)
GO

CREATE TABLE [Product] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Name] NVARCHAR(255) NOT NULL,
  [Description] NVARCHAR(MAX),
  [CategoryId] INT NOT NULL,
  [BrandId] INT NOT NULL,
  [MaterialId] INT NOT NULL,
  [SoleId] INT NOT NULL,
  [Status] INT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE()),
  [CreatedBy] INT NOT NULL,
  [UpdatedBy] INT NOT NULL
)
GO

CREATE TABLE [ProductImage] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [ImageUrl] VARCHAR(255) NOT NULL,
  [ProductId] INT NOT NULL,
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE()),
  [CreatedBy] INT NOT NULL,
  [UpdatedBy] INT NOT NULL
)
GO

CREATE TABLE [ProductDetail] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] VARCHAR(50) UNIQUE NOT NULL,
  [Quantity] INT NOT NULL DEFAULT (0),
  [Price] DECIMAL(10,0) NOT NULL,
  [Weight] INT NOT NULL,
  [ProductId] INT NOT NULL,
  [SizeId] INT NOT NULL,
  [ColorId] INT NOT NULL,
  [PromotionId] INT,
  [Status] INT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE()),
  [CreatedBy] INT NOT NULL,
  [UpdatedBy] INT NOT NULL
)
GO

CREATE TABLE [Order] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] NVARCHAR(250) UNIQUE NOT NULL,
  [UserId] INT NOT NULL,
  [EmployeeId] INT NOT NULL,
  [VoucherId] INT,
  [OrderPayMentId] INT,
  [RecipientName] NVARCHAR(255),
  [RecipientPhone] NVARCHAR(20),
  [TransactionCode] NVARCHAR(50),
  [OrderStatus] INT,
  [ShippingStatus] INT,
  [PaymentDate] DATETIME,
  [Subtotal] DECIMAL(10,2) NOT NULL,
  [ShippingCost] DECIMAL(10,2),
  [Total] DECIMAL(10,2) NOT NULL,
  [OrderType] NVARCHAR(50),
  [TrackingNumber] VARCHAR(255),
  [Notes] NVARCHAR(MAX),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE()),
  [CreatedBy] INT NOT NULL,
  [UpdatedBy] INT NOT NULL
)
GO

CREATE TABLE [Payment] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [PaymentMethod] NVARCHAR(255) NOT NULL
)
GO

CREATE TABLE [OrderPayment] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [OrderId] INT NOT NULL,
  [PaymentId] INT NOT NULL,
  [Transaction] VARCHAR(255),
  [PaymentDate] DATETIME,
  [Amount] DECIMAL(10,0),
  [Status] int,
  [Note] NVARCHAR(255)
)
GO

CREATE TABLE [OrderDetail] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [OrderId] INT NOT NULL,
  [ProductDetailId] INT NOT NULL,
  [Quantity] INT NOT NULL,
  [Price] DECIMAL(10,0) NOT NULL
)
GO

CREATE TABLE [Cart] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [UserId] INT UNIQUE NOT NULL
)
GO

CREATE TABLE [CartDetail] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [CartId] INT NOT NULL,
  [ProductDetailId] INT NOT NULL,
  [Quantity] INT NOT NULL
)
GO

CREATE TABLE [Promotion] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] NVARCHAR(255) UNIQUE NOT NULL,
  [Name] NVARCHAR(255) NOT NULL,
  [StartDate] DATETIME NOT NULL,
  [EndDate] DATETIME NOT NULL,
  [Quantity] INT NOT NULL,
  [DiscountPercentage] DECIMAL(5,2),
  [Status] INT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE()),
  [CreatedBy] INT NOT NULL,
  [UpdatedBy] INT NOT NULL
)
GO

CREATE TABLE [CustomerVoucher] (
  [Id] int UNIQUE NOT NULL,
  [CustomerId] int NOT NULL,
  [VoucherId] int NOT NULL,
  [UsageCount] int,
  [IsSaved] BIT
)
GO

CREATE TABLE [Voucher] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] NVARCHAR(255) UNIQUE NOT NULL,
  [Name] NVARCHAR(255) NOT NULL,
  [StartDate] DATETIME NOT NULL,
  [EndDate] DATETIME NOT NULL,
  [DiscountAmount] DECIMAL(10,0),
  [DiscountPercentage] DECIMAL(5,2),
  [VoucherType] NVARCHAR(50) NOT NULL,
  [MinimumDiscountAmount] DECIMAL(10,0),
  [MaximumDiscountAmount] DECIMAL(10,0),
  [Quantity] INT NOT NULL,
  [Status] INT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE())
)
GO

CREATE TABLE [Size] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] NVARCHAR(255) UNIQUE NOT NULL,
  [Name] NVARCHAR(100) NOT NULL,
  [Status] INT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE()),
  [CreatedBy] INT NOT NULL,
  [UpdatedBy] INT NOT NULL
)
GO

CREATE TABLE [Color] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] NVARCHAR(255) UNIQUE NOT NULL,
  [Name] NVARCHAR(100) NOT NULL,
  [Status] INT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE()),
  [CreatedBy] INT NOT NULL,
  [UpdatedBy] INT NOT NULL
)
GO

CREATE TABLE [Category] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] NVARCHAR(255) UNIQUE NOT NULL,
  [Name] NVARCHAR(255) NOT NULL,
  [Status] INT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE()),
  [CreatedBy] INT NOT NULL,
  [UpdatedBy] INT NOT NULL
)
GO

CREATE TABLE [Brand] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] NVARCHAR(255) UNIQUE NOT NULL,
  [Name] NVARCHAR(255) NOT NULL,
  [Status] INT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE()),
  [CreatedBy] INT NOT NULL,
  [UpdatedBy] INT NOT NULL
)
GO

CREATE TABLE [Material] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] NVARCHAR(255) UNIQUE NOT NULL,
  [Name] NVARCHAR(255) NOT NULL,
  [Status] INT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE()),
  [CreatedBy] INT NOT NULL,
  [UpdatedBy] INT NOT NULL
)
GO

CREATE TABLE [Sole] (
  [Id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Code] NVARCHAR(255) UNIQUE NOT NULL,
  [Name] NVARCHAR(255) NOT NULL,
  [Status] INT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  [UpdatedAt] DATETIME DEFAULT (GETDATE()),
  [CreatedBy] INT NOT NULL,
  [UpdatedBy] INT NOT NULL
)
GO

ALTER TABLE [Cart] ADD FOREIGN KEY ([UserId]) REFERENCES [Customer] ([Id])
GO

ALTER TABLE [ProductImage] ADD FOREIGN KEY ([ProductId]) REFERENCES [Product] ([Id])
GO

ALTER TABLE [Product] ADD FOREIGN KEY ([MaterialId]) REFERENCES [Material] ([Id])
GO

ALTER TABLE [Product] ADD FOREIGN KEY ([BrandId]) REFERENCES [Brand] ([Id])
GO

ALTER TABLE [Product] ADD FOREIGN KEY ([CategoryId]) REFERENCES [Category] ([Id])
GO

ALTER TABLE [Product] ADD FOREIGN KEY ([SoleId]) REFERENCES [Sole] ([Id])
GO

ALTER TABLE [ProductDetail] ADD FOREIGN KEY ([ColorId]) REFERENCES [Color] ([Id])
GO

ALTER TABLE [ProductDetail] ADD FOREIGN KEY ([SizeId]) REFERENCES [Size] ([Id])
GO

ALTER TABLE [ProductDetail] ADD FOREIGN KEY ([PromotionId]) REFERENCES [Promotion] ([Id])
GO

ALTER TABLE [OrderPayment] ADD FOREIGN KEY ([OrderId]) REFERENCES [Order] ([Id])
GO

ALTER TABLE [OrderPayment] ADD FOREIGN KEY ([PaymentId]) REFERENCES [Payment] ([Id])
GO

ALTER TABLE [OrderDetail] ADD FOREIGN KEY ([OrderId]) REFERENCES [Order] ([Id])
GO

ALTER TABLE [OrderDetail] ADD FOREIGN KEY ([ProductDetailId]) REFERENCES [ProductDetail] ([Id])
GO

ALTER TABLE [Order] ADD FOREIGN KEY ([VoucherId]) REFERENCES [Voucher] ([Id])
GO

ALTER TABLE [CustomerVoucher] ADD FOREIGN KEY ([CustomerId]) REFERENCES [Customer] ([Id])
GO

ALTER TABLE [Address] ADD FOREIGN KEY ([CustomerId]) REFERENCES [Customer] ([Id])
GO

ALTER TABLE [Order] ADD FOREIGN KEY ([EmployeeId]) REFERENCES [Employee] ([Id])
GO

ALTER TABLE [CartDetail] ADD FOREIGN KEY ([ProductDetailId]) REFERENCES [ProductDetail] ([Id])
GO

ALTER TABLE [CartDetail] ADD FOREIGN KEY ([CartId]) REFERENCES [Cart] ([Id])
GO

ALTER TABLE [ProductDetail] ADD FOREIGN KEY ([ProductId]) REFERENCES [Product] ([Id])
GO

ALTER TABLE [CustomerVoucher] ADD FOREIGN KEY ([Id]) REFERENCES [Voucher] ([Id])
GO
