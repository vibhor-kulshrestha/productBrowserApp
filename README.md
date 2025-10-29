# Product Browser - Kotlin Multiplatform Mobile App

A cross-platform mobile application built with Kotlin Multiplatform and Compose Multiplatform that allows users to browse, search, and view product details from the DummyJSON API.

## Business Requirements Summary

### Overview
The Product Browser application is designed to provide a seamless, cross-platform mobile experience for users to discover, search, and explore product information from a centralized product catalog. The application addresses the need for a unified product browsing experience across both Android and iOS platforms while maintaining a single codebase for core business logic.

### Functional Requirements

#### 1. Product Discovery & Browsing
- **Product List View**: Users must be able to view a scrollable list of available products
    - Display essential product information: title, brand, price, and thumbnail image
    - Show product rating with visual indicators (star icons)
    - Support pagination metadata handling (total count, skip, limit)
- **Product Detail View**: Users must access comprehensive product information
    - Display full product details including:
        - Product title and brand
        - Detailed description
        - Pricing information (base price and discount percentage)
        - Customer rating (0-5 stars)
        - Stock availability
        - Product category
        - Product images (thumbnail and full image gallery)
    - Navigation between list and detail views

#### 2. Search & Filter Functionality
- **Product Search**: Users must be able to search products by keyword
    - Real-time search as user types
    - Search across product titles, descriptions, and other attributes
    - Display search results dynamically
    - Clear search functionality to return to full product list
- **Category Filtering**: Users should be able to filter products by category (API-supported feature)

#### 3. User Experience & State Management Requirements
- **Data Loading States**: Application must display loading indicators during data fetch operations
- **Error Handling**: Application must gracefully handle and display errors with retry mechanisms
    - Network connectivity issues
    - API failures
    - Invalid search queries
    - Product not found scenarios
- **Empty States**: Application must display appropriate messages when:
    - No products are available
    - Search yields no results
    - Product details cannot be loaded

### Technical Requirements

#### 1. Cross-Platform Compatibility
- **Multi-Platform Support**: Single codebase must run on both Android and iOS
- **Shared Business Logic**: Core application logic, data models, and business rules must be shared across platforms
- **Platform-Specific UI**: UI should adapt to platform-specific design guidelines while maintaining shared components

#### 2. Architecture & Code Quality
- **Clean Architecture**: Implementation must follow Clean Architecture principles with clear separation of concerns
    - **Data Layer**: API communication, DTO transformation, repository implementations
    - **Domain Layer**: Business logic, use cases, domain models, repository interfaces
    - **Presentation Layer**: UI components, ViewModels, navigation logic
- **Testability**: Code must be structured to support unit testing of business logic and repository functionality
- **Maintainability**: Code organization must support future enhancements and feature additions

#### 3. Data Integration
- **API Integration**: Application must integrate with DummyJSON Products API
    - Endpoint: `https://dummyjson.com`
    - Supported operations:
        - Fetch all products (`GET /products`)
        - Search products (`GET /products/search?q={keyword}`)
        - Get product by ID (`GET /products/{id}`)
        - Filter by category (`GET /products/category/{category}`)
- **Data Models**: Application must handle the following product attributes:
    - Basic information: ID, title, description
    - Pricing: Price, discount percentage
    - Classification: Brand, category
    - Inventory: Stock availability
    - Quality metrics: Customer rating
    - Media: Thumbnail URL, image gallery URLs

#### 4. User Experience Requirements
- **Responsive UI**: Interface must adapt to different screen sizes and orientations
- **Image Loading**: Product images must load efficiently with placeholder/loading states
- **Performance**: Application must provide smooth scrolling and responsive interactions
- **Navigation**: Intuitive navigation flow between screens with back navigation support
- **Visual Design**: Modern Material Design 3 interface with consistent styling

### Non-Functional Requirements

#### 1. Development Constraints
- **Time Constraint**: Initial implementation designed for completion within a single workday
- **Platform Versions**:
    - Android: Minimum SDK support as defined in build configuration
    - iOS: iOS 14+ support
- **Kotlin Version**: Compatibility with Kotlin 1.9.0+

#### 2. Scalability Considerations
- **Code Reusability**: Maximum code sharing between platforms (estimated 80%+ shared code)
- **Extensibility**: Architecture must support future features such as:
    - Offline data persistence/caching
    - User favorites/wishlists
    - Product comparisons
    - Enhanced filtering and sorting options
    - Pagination support

#### 3. Quality Assurance
- **Error Resilience**: Application must not crash on network errors or invalid API responses
- **Data Validation**: Input validation for search queries and category filters
- **State Management**: Proper state handling for loading, success, and error scenarios

### Assumptions & Constraints

#### Data Source Assumptions
- DummyJSON API provides reliable, consistent product data
- API responses follow documented schema with all required fields
- Network connectivity is available during usage (no offline-first requirement)

#### Implementation Constraints
- Single-threaded API calls (no concurrent request optimization required initially)
- No local data persistence or caching required in initial version
- No user authentication or personalization features required
- No e-commerce functionality (browse-only, no purchasing)

#### Future Enhancement Opportunities
- Offline support with local database/caching
- Enhanced image loading with caching and optimization
- Advanced filtering and sorting capabilities
- User favorites and product comparison features
- Deep linking support
- Push notifications for new products

## Project Summary

This application provides a clean, modern interface for browsing products with the following key features:
- **Product List**: Display products with name, price, and thumbnail images
- **Product Details**: View comprehensive product information including description, brand, rating
- **Search Functionality**: Search products by keyword using the API
- **Cross-Platform**: Runs on both Android and iOS with shared business logic

## Tech Stack

- **Kotlin Multiplatform**: Shared business logic across platforms
- **Compose Multiplatform**: Shared UI components for Android and iOS
- **Ktor Client**: HTTP client for API requests
- **kotlinx.serialization**: JSON parsing and serialization
- **Coroutines & Flow**: Asynchronous programming and reactive streams
- **Clean Architecture**: Separation of concerns with data, domain, and presentation layers

## Architecture Overview

The project follows Clean Architecture principles with clear separation of concerns:

```
├── data/
│   ├── dto/           # Data Transfer Objects for API responses
│   ├── api/           # API client implementation
│   └── repository/    # Repository implementations
├── domain/
│   ├── model/         # Domain models
│   ├── repository/    # Repository interfaces
│   └── usecase/       # Business logic use cases
└── presentation/
    ├── ui/            # Compose UI screens
    ├── viewmodel/     # ViewModels with StateFlow
    └── navigation/    # Navigation logic
```

### Key Components

- **Data Layer**: Handles API communication, data transformation, and local storage
- **Domain Layer**: Contains business logic, use cases, and repository interfaces
- **Presentation Layer**: UI components, ViewModels, and navigation logic

## APIs Used

- **DummyJSON Products API**: https://dummyjson.com/docs/products
    - `GET /products` - Fetch all products
    - `GET /products/search?q={keyword}` - Search products
    - `GET /products/{id}` - Get product details
    - `GET /products/category/{category}` - Filter by category (optional)

## How to Build & Run

### Prerequisites

- **Android**: Android Studio Arctic Fox or later
- **iOS**: Xcode 14+ and iOS 14+ simulator
- **Kotlin**: 1.9.0+
- **Gradle**: 8.0+

### Android

1. Open the project in Android Studio
2. Sync Gradle files
3. Run the app on an Android device or emulator:
   ```bash
   ./gradlew :composeApp:installDebug
   ```

### iOS

1. Open `iosApp/iosApp.xcodeproj` in Xcode
2. Select your target device or simulator
3. Build and run the project (⌘+R)

### Building from Command Line

```bash
# Build for Android
./gradlew :composeApp:assembleDebug

# Build for iOS (requires Xcode)
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64
```

## Tests

Run unit tests for the shared module:

```bash
./gradlew :composeApp:testDebugUnitTest
```

The project includes unit tests for:
- Product repository functionality
- Search use case validation
- Data transformation logic

## Trade-offs & Assumptions

### Implemented Features
- ✅ Product listing with thumbnails
- ✅ Product detail view
- ✅ Search functionality
- ✅ Clean architecture implementation
- ✅ Cross-platform compatibility
- ✅ Unit tests for core functionality

### Design Decisions
- **UI Framework**: Used Compose Multiplatform for maximum code sharing
- **State Management**: StateFlow for reactive UI updates
- **Architecture**: Clean Architecture for maintainability and testability
- **API Client**: Ktor for its excellent multiplatform support
- **Navigation**: Simple navigation without complex routing (time constraint)

### Limitations & Future Enhancements
- **Pagination**: Currently loads all products at once (API limitation)
- **Caching**: No offline support or data persistence
- **Error Handling**: Basic error states (could be enhanced with retry mechanisms)
- **UI Polish**: Material Design defaults (custom theming could be added)
- **Category Filtering**: Basic implementation (could be expanded)
- **Image Loading**: Simple implementation (could add caching/optimization)

### Time Constraints
This project was designed to be completed within a single workday, prioritizing:
1. Core functionality over advanced features
2. Clean architecture over UI polish
3. Cross-platform compatibility over platform-specific optimizations

## Project Structure

```
productBrowserApp/
├── composeApp/                    # Main shared module
│   ├── src/
│   │   ├── commonMain/
│   │   │   ├── kotlin/
│   │   │   │   ├── data/         # Data layer
│   │   │   │   ├── domain/       # Domain layer
│   │   │   │   └── presentation/ # Presentation layer
│   │   │   └── resources/         # Shared resources
│   │   ├── androidMain/          # Android-specific code
│   │   ├── iosMain/              # iOS-specific code
│   │   └── commonTest/           # Shared unit tests
│   └── build.gradle.kts
├── iosApp/                       # iOS app wrapper
└── build.gradle.kts             # Root build configuration
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.