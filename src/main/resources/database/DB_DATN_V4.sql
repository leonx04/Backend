USE master;
GO
DROP DATABASE IF EXISTS DB_DATN_V4; -- Sử dụng IF EXISTS để tránh lỗi nếu cơ sở dữ liệu không tồn tại
GO

CREATE DATABASE DB_DATN_V4;
GO
USE DB_DATN_V4;
GO

CREATE TABLE [Employee] (
    [id]         INT           PRIMARY KEY  NOT NULL IDENTITY(1, 1), -- ID nhân viên

    [code]       NVARCHAR(255) UNIQUE       NOT NULL,                 -- Mã nhân viên
    [userName]   NVARCHAR(100) UNIQUE       NOT NULL,                 -- Tên đăng nhập
    [password]   NVARCHAR(255)               NOT NULL,                 -- Mật khẩu

    [fullName]   NVARCHAR(255),                                         -- Họ và tên
    [gender]     INT,                                                     -- Giới tính
    [birthDate]  DATE,                                                    -- Ngày sinh
    [phone]      NVARCHAR(20),                                           -- Số điện thoại
    [email]      NVARCHAR(255),                                          -- Địa chỉ email
    [address]    NVARCHAR(255),                                          -- Địa chỉ
    [imageUrl]   NVARCHAR(255),                                          -- URL hình ảnh

    [roleId]     INT           DEFAULT (2),                              -- ID vai trò	(1.Manage / 2.Nhân viên)
    [status]     INT           DEFAULT (1),                              -- Trạng thái
    [note]       NVARCHAR(MAX),                                          -- Ghi chú
    [createdAt]  DateTime      DEFAULT (GETDATE()),                     -- Thời gian tạo
    [updatedAt]  DateTime      DEFAULT (GETDATE())                      -- Thời gian cập nhật
)
GO


CREATE TABLE [Customer] (
    [id]         INT           PRIMARY KEY NOT NULL IDENTITY(1, 1), -- ID khách hàng

    [code]       NVARCHAR(255)               NOT NULL,                 -- Mã khách hàng

    [userName]   NVARCHAR(100)               NOT NULL,                 -- Tên đăng nhập
    [password]   NVARCHAR(255)               NOT NULL,                 -- Mật khẩu
    [fullName]   NVARCHAR(255),                                         -- Họ và tên

    [gender]     INT,                                                     -- Giới tính
    [email]      NVARCHAR(255)               NOT NULL,                 -- Địa chỉ email
    [phone]      VARCHAR(20),                                           -- Số điện thoại
    [birthDate]  DATE,                                                    -- Ngày sinh
    [imageUrl]   NVARCHAR(255),                                          -- URL hình ảnh

    [status]     INT           DEFAULT (1),                              --Trạng thái (1: kích hoạt, 2: vô hiệu hóa)
    [createdAt]  DateTime          DEFAULT (GETDATE()),                     -- Thời gian tạo
    [updatedAt]  DateTime          DEFAULT (GETDATE())                      -- Thời gian cập nhật
)
GO


CREATE TABLE [Address] (
    [id]            INT           PRIMARY KEY NOT NULL IDENTITY(1, 1), -- ID địa chỉ

    [customerId]    INT                           NOT NULL,              -- ID khách hàng
    [detailAddress] NVARCHAR(255)                  NOT NULL,              -- Mô tả địa chỉ chi tiết

    [recipientName] NVARCHAR(255),                                         -- Tên người nhận
    [recipientPhone] NVARCHAR(20),                                         -- Số điện thoại người nhận

    [city]          NVARCHAR(255)                  NOT NULL,              -- Thành phố
    [district]      NVARCHAR(255)                  NOT NULL,              -- Quận/Huyện
    [commune]       NVARCHAR(255)                  NOT NULL,              -- Xã/Phường

    [status]        INT                           NOT NULL,              -- Trạng thái
    [createdAt]     Datetime          DEFAULT (GETDATE()),              -- Thời gian tạo
    [updatedAt]     Datetime          DEFAULT (GETDATE())               -- Thời gian cập nhật
	-- Tổng số thuộc tính: 12
)
GO


CREATE TABLE [Product] (
    [id]          INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),   -- Khóa chính, tự động tăng

    [name]        NVARCHAR(255) NOT NULL,                              -- Tên sản phẩm
    [description] NVARCHAR(MAX),                                       -- Mô tả sản phẩm

    [categoryId]  INT           NOT NULL,                              -- ID danh mục
    [brandId]     INT           NOT NULL,                              -- ID thương hiệu
    [materialId]  INT           NOT NULL,                              -- ID chất liệu
    [soleId]      INT           NOT NULL,                              -- ID đế

    [status]      INT           DEFAULT (1),                          -- Trạng thái
    [createdAt]   DATETIME      DEFAULT (GETDATE()),                 -- Ngày tạo
    [updatedAt]   DATETIME      DEFAULT (GETDATE()),                 -- Ngày cập nhật
    [createdBy]   INT           NOT NULL,                              -- Người tạo
    [updatedBy]   INT           NOT NULL                               -- Người cập nhật
    -- Tổng số thuộc tính: 12
)
GO



CREATE TABLE [ProductImage] (
  [id]         INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),   -- Khóa chính, tự động tăng

  [imageUrl]   VARCHAR(255)  NOT NULL,                              -- Đường dẫn hình ảnh
  [productVariantId]  INT           NOT NULL,                              -- ID sản phẩm

  [createdAt]  DATETIME      DEFAULT (GETDATE()),                 -- Ngày tạo
  [updatedAt]  DATETIME      DEFAULT (GETDATE()),                 -- Ngày cập nhật
  [createdBy]  INT           NOT NULL,                              -- Người tạo
  [updatedBy]  INT           NOT NULL                               -- Người cập nhật
  -- Tổng số thuộc tính: 7
)
GO


CREATE TABLE [ProductVariant] (
    [id]           INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),   -- Khóa chính, tự động tăng

    [SKU]         VARCHAR(50)   UNIQUE        NOT NULL,                  -- Tên sản phẩm chi tiết
    [quantity]     INT           NOT NULL      DEFAULT (0),               -- Số lượng
    [price]        DECIMAL(10,0) NOT NULL,                               -- Giá
    [weight]       INT           NOT NULL,                               -- Cân nặng

    [productId]    INT           NOT NULL,                               -- ID sản phẩm
    [sizeId]       INT           NOT NULL,                               -- ID kích thước
    [colorId]      INT           NOT NULL,                               -- ID màu sắc

    [promotionId]  INT,                                            -- ID khuyến mãi

    [status]       INT           DEFAULT (1),                          -- Trạng thái
    [createdAt]    DATETIME      DEFAULT (GETDATE()),                 -- Ngày tạo
    [updatedAt]    DATETIME      DEFAULT (GETDATE()),                 -- Ngày cập nhật
    [createdBy]    INT           NOT NULL,                              -- Người tạo
    [updatedBy]    INT           NOT NULL                               -- Người cập nhật
    -- Tổng số thuộc tính: 13
)
GO


CREATE TABLE [Orders] (
  [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [Code] NVARCHAR(250) UNIQUE NOT NULL,
  [OrdersAddress] NVARCHAR(255) NOT NULL,
  [RecipientName] NVARCHAR(255),
  [RecipientPhone] NVARCHAR(20),
  [VoucherId] INT,
  [CustomerId] INT,
  [OrderPayment] INT NOT NULL,
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
  [UpdatedAt] DATETIME DEFAULT (GETDATE())
)
GO
CREATE TABLE [OrderStatusLog] (
  [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [EmployeeId] INT NOT NULL,
  [Orderid] INT NOT NULL,
  [OrderStatus] INT NOT NULL,
  [UpdatedAt] DATETIME DEFAULT (GETDATE())
)
GO



CREATE TABLE [OrderDetail] (
    [id]              INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),    -- Khóa chính, tự động tăng
    [orderId]        INT           NOT NULL,                                 -- ID đơn hàng
    [productVariantId] INT           NOT NULL,                                 -- ID chi tiết sản phẩm

    [quantity]       INT           NOT NULL,                                 -- Số lượng
    [price]          DECIMAL(10,0) NOT NULL                               -- Giá
    -- Tổng số thuộc tính: 5
)
GO

CREATE TABLE [Cart] (
    [id]       INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),    -- Khóa chính, tự động tăng
    [userId]   INT           UNIQUE        NOT NULL                     -- ID người dùng
    -- Tổng số thuộc tính: 2
)
GO


CREATE TABLE [CartDetail] (
    [id]              INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),    -- Khóa chính, tự động tăng
    [cartId]         INT           NOT NULL,                                 -- ID giỏ hàng
    [productVariantId] INT           NOT NULL,                                 -- ID chi tiết sản phẩm

    [quantity]       INT           NOT NULL                                  -- Số lượng
    -- Tổng số thuộc tính: 4
)
GO


CREATE TABLE [Promotion] (
  [id] INT PRIMARY KEY NOT NULL IDENTITY(1, 1),

  [code] NVARCHAR(255) UNIQUE NOT NULL,
  [name] NVARCHAR(255) NOT NULL,
  [startDate] DATETIME NOT NULL,
  [endDate] DATETIME NOT NULL,
  [discountPercentage] DECIMAL(5,2),

  [status] INT DEFAULT (1),

  [createdAt] DATETIME DEFAULT (GETDATE()),
  [updatedAt] DATETIME DEFAULT (GETDATE())
)
GO

CREATE TABLE [Voucher] (
    [id]                     INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),    -- Khóa chính, tự động tăng

    [code]                   NVARCHAR(255) UNIQUE        NOT NULL,                   -- Mã voucher
    [description]            NVARCHAR(255) NOT NULL,                                -- Mô tả voucher
    [startDate]             DATETIME      NOT NULL,                                 -- Ngày bắt đầu
    [endDate]               DATETIME      NOT NULL,                                 -- Ngày kết thúc

    [discountValue]         DECIMAL(15,0),                                      -- Giá trị giảm giá
    [voucherType]           NVARCHAR(50) NOT NULL,                                -- Loại voucher

    [minimumOrderValue]     DECIMAL(10,0),                                      -- Giá trị đơn hàng tối thiểu
    [maximumDiscountAmount]  DECIMAL(10,0),                                      -- Số tiền giảm tối đa
    [quantity]              INT           NOT NULL,                                 -- Số lượng

    [status]                INT           DEFAULT (1),                             -- Trạng thái
    [createdAt]             DATETIME      DEFAULT (GETDATE()),                     -- Ngày tạo
    [updatedAt]             DATETIME      DEFAULT (GETDATE())                     -- Ngày cập nhật
    -- Tổng số thuộc tính: 13
)
GO


CREATE TABLE [Size] (
    [id]          INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),    -- Khóa chính, tự động tăng
    [name]        NVARCHAR(100) NOT NULL,                                -- Tên kích thước
    [status]      INT           DEFAULT (1),                             -- Trạng thái
    [createdAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày tạo
    [updatedAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày cập nhật
    [createdBy]   INT           NOT NULL,                               -- Người tạo
    [updatedBy]   INT           NOT NULL                               -- Người cập nhật
    -- Tổng số thuộc tính: 7
)
GO


CREATE TABLE [Color] (
    [id]          INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),    -- Khóa chính, tự động tăng
    [name]        NVARCHAR(100) NOT NULL,                                -- Tên màu sắc
    [status]      INT           DEFAULT (1),                             -- Trạng thái
    [createdAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày tạo
    [updatedAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày cập nhật
    [createdBy]   INT           NOT NULL,                               -- Người tạo
    [updatedBy]   INT           NOT NULL                               -- Người cập nhật
    -- Tổng số thuộc tính: 7
)
GO


CREATE TABLE [Category] (
    [id]          INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),    -- Khóa chính, tự động tăng
    [name]        NVARCHAR(100) NOT NULL,                                -- Tên danh mục
    [status]      INT           DEFAULT (1),                             -- Trạng thái
    [createdAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày tạo
    [updatedAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày cập nhật
    [createdBy]   INT           NOT NULL,                               -- Người tạo
    [updatedBy]   INT           NOT NULL                               -- Người cập nhật
    -- Tổng số thuộc tính: 7
)
GO


CREATE TABLE [Brand] (
    [id]          INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),    -- Khóa chính, tự động tăng
    [name]        NVARCHAR(100) NOT NULL,                                -- Tên thương hiệu
    [status]      INT           DEFAULT (1),                             -- Trạng thái
    [createdAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày tạo
    [updatedAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày cập nhật
    [createdBy]   INT           NOT NULL,                               -- Người tạo
    [updatedBy]   INT           NOT NULL                               -- Người cập nhật
    -- Tổng số thuộc tính: 7
)
GO


CREATE TABLE [Material] (
    [id]          INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),    -- Khóa chính, tự động tăng
    [name]        NVARCHAR(100) NOT NULL,                                -- Tên chất liệu
    [status]      INT           DEFAULT (1),                             -- Trạng thái
    [createdAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày tạo
    [updatedAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày cập nhật
    [createdBy]   INT           NOT NULL,                               -- Người tạo
    [updatedBy]   INT           NOT NULL                               -- Người cập nhật
    -- Tổng số thuộc tính: 7
)
GO


CREATE TABLE [Sole] (
    [id]          INT           PRIMARY KEY   NOT NULL IDENTITY(1, 1),    -- Khóa chính, tự động tăng
    [name]        NVARCHAR(100) NOT NULL,                                -- Tên đế
    [status]      INT           DEFAULT (1),                             -- Trạng thái
    [createdAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày tạo
    [updatedAt]   DATETIME      DEFAULT (GETDATE()),                    -- Ngày cập nhật
    [createdBy]   INT           NOT NULL,                               -- Người tạo
    [updatedBy]   INT           NOT NULL                               -- Người cập nhật
    -- Tổng số thuộc tính: 7
)
GO

ALTER TABLE [OrderStatusLog] ADD FOREIGN KEY ([EmployeeId]) REFERENCES [Employee] ([Id])
GO

ALTER TABLE [OrderStatusLog] ADD FOREIGN KEY ([Orderid]) REFERENCES [Orders] ([Id])
GO
ALTER TABLE [Orders] ADD FOREIGN KEY ([CustomerId]) REFERENCES [Customer] ([Id])
GO
ALTER TABLE [Orders] ADD FOREIGN KEY ([VoucherId]) REFERENCES [Voucher] ([Id])
GO
-- ------------------------------------------
-- Ràng buộc khóa ngoại cho bảng Cart
-- ------------------------------------------
ALTER TABLE [Cart] 
ADD FOREIGN KEY ([userId]) REFERENCES [Customer] ([id]);
GO

-- ------------------------------------------
-- Ràng buộc khóa ngoại cho bảng Product
-- ------------------------------------------
ALTER TABLE [Product] 
ADD FOREIGN KEY ([materialId]) REFERENCES [Material] ([id]),
    FOREIGN KEY ([brandId]) REFERENCES [Brand] ([id]),
    FOREIGN KEY ([categoryId]) REFERENCES [Category] ([id]),
    FOREIGN KEY ([soleId]) REFERENCES [Sole] ([id]);
GO
-- ------------------------------------------
-- Ràng buộc khóa ngoại cho bảng ProductImage
-- ------------------------------------------
ALTER TABLE [ProductImage] 
ADD FOREIGN KEY ([productVariantId]) REFERENCES [ProductVariant] ([id]);
GO

-- ------------------------------------------
-- Ràng buộc khóa ngoại cho bảng [ProductVariant]
-- ------------------------------------------
ALTER TABLE [ProductVariant] 
ADD FOREIGN KEY ([colorId]) REFERENCES [Color] ([id]),
    FOREIGN KEY ([sizeId]) REFERENCES [Size] ([id]),
    FOREIGN KEY ([promotionId]) REFERENCES [Promotion] ([id]),
    FOREIGN KEY ([productId]) REFERENCES [Product] ([id]);
GO

-- ------------------------------------------
-- Ràng buộc khóa ngoại cho bảng OrderDetail
-- ------------------------------------------
ALTER TABLE [OrderDetail] 
ADD FOREIGN KEY ([orderId]) REFERENCES [Orders] ([id]),
    FOREIGN KEY ([productVariantId]) REFERENCES [ProductVariant] ([id]);
GO
-- ------------------------------------------
-- Ràng buộc khóa ngoại cho bảng Orders
-- ------------------------------------------
--ALTER TABLE [Orders] 
--ADD FOREIGN KEY ([voucherId]) REFERENCES [Voucher] ([id]),
--    --FOREIGN KEY ([employeeId]) REFERENCES [Employee] ([id]),
--    --FOREIGN KEY ([userId]) REFERENCES [Customer] ([id]),
--    --FOREIGN KEY ([orderPaymentId]) REFERENCES [OrderPayment] ([id]);
--GO

-- ------------------------------------------
-- Ràng buộc khóa ngoại cho bảng CartDetail
-- ------------------------------------------
ALTER TABLE [CartDetail] 
ADD FOREIGN KEY ([productVariantId]) REFERENCES [ProductVariant] ([id]),
    FOREIGN KEY ([cartId]) REFERENCES [Cart] ([id]);
GO

-- ------------------------------------------
-- Ràng buộc khóa ngoại cho bảng Address
-- ------------------------------------------
ALTER TABLE [Address] 
ADD FOREIGN KEY ([customerId]) REFERENCES [Customer] ([id]);
GO

--select * from Customer
--select * from Employee

--select * from Brand



