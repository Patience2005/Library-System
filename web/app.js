// Library Management System Web Frontend
class LibraryWebApp {
    constructor() {
        this.books = [];
        this.currentView = 'dashboard';
        this.init();
    }

    init() {
        this.loadBooksFromDatabase();
        this.setupEventListeners();
        // Start with dashboard view
        window.showView('dashboard');
    }

    // Load books from the Java backend database
    async loadBooksFromDatabase() {
        try {
            // Try to load from the web server API
            const response = await fetch('http://localhost:8080/api/books');
            if (response.ok) {
                const booksData = await response.json();
                this.books = booksData;
                this.updateAllViews();
                return;
            }
        } catch (error) {
            console.log('Web server not available, using sample data');
        }
        
        // Fallback to sample data
        console.log('Using sample data for demonstration');
        this.books = [
            { isbn: '978-0-13-468599-1', title: 'Effective Java', author: 'Joshua Bloch', type: 'Fiction', category: 'Programming', available: true },
            { isbn: '978-0-321-35668-0', title: 'Clean Code', author: 'Robert C. Martin', type: 'Fiction', category: 'Software Engineering', available: true },
            { isbn: '978-1-4919-5076-6', title: 'Introduction to Algorithms', author: 'Thomas H. Cormen', type: 'NonFiction', category: 'Computer Science', available: true },
            { isbn: '978-0-262-03293-3', title: 'Structure and Interpretation', author: 'Harold Abelson', type: 'NonFiction', category: 'Programming', available: true },
            { isbn: '978-0-13-235088-4', title: 'Java Language Specification', author: 'James Gosling', type: 'Reference', category: 'Reference', available: true },
            { isbn: '978-0-07-180855-3', title: 'Java: The Complete Reference', author: 'Herbert Schildt', type: 'Fiction', category: 'Programming', available: true },
            { isbn: '978-1-4493-3187-0', title: 'Head First Java', author: 'Kathy Sierra', type: 'Fiction', category: 'Education', available: true },
            { isbn: '978-0-321-76572-3', title: 'The C++ Programming Language', author: 'Bjarne Stroustrup', type: 'NonFiction', category: 'Computer Science', available: true },
            { isbn: '978-0-13-110362-7', title: 'The C Programming Language', author: 'Brian Kernighan', type: 'NonFiction', category: 'Programming', available: true },
            { isbn: '978-0-13-468599-2', title: 'Python Documentation', author: 'Python Software Foundation', type: 'Reference', category: 'Reference', available: true },
            { isbn: '978-0-13-46789-9', title: 'Effective C++', author: 'Scott Meyers', type: 'Fiction', category: 'Programming', available: true },
            { isbn: '978-0-14-29564-5', title: 'Harry Potter and the Sorcerer\'s Stone', author: 'JK Rowling', type: 'Fiction', category: 'Fantasy', available: true },
            { isbn: '978-0-14-45326-6', title: 'Harry Potter and the Chamber of Secrets', author: 'JK Rowling', type: 'Fiction', category: 'Fantasy', available: true },
            { isbn: '978-0-13-468599-8', title: 'Here fo the fun', author: 'JOhn', type: 'NonFiction', category: 'Sasing', available: true }
        ];
        this.updateAllViews();
    }

    parseBooksFromText(text) {
        const lines = text.trim().split('\n');
        return lines.map(line => {
            const [isbn, title, author, available, type, category] = line.split(',');
            return {
                isbn: isbn.trim(),
                title: title.trim(),
                author: author.trim(),
                type: type.trim(),
                category: category.trim(),
                available: available.trim().toLowerCase() === 'true'
            };
        });
    }

    setupEventListeners() {
        // Add book form
        document.getElementById('addBookForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.addBook();
        });

        // Search input
        document.getElementById('searchInput').addEventListener('input', (e) => {
            this.performSearch(e.target.value);
        });
    }

    updateAllViews() {
        this.updateDashboard();
        this.updateBooksTable();
        this.updateCategories();
    }

    updateDashboard() {
        const totalBooks = this.books.length;
        const availableBooks = this.books.filter(book => book.available).length;
        const fictionBooks = this.books.filter(book => book.type === 'Fiction').length;
        const nonFictionBooks = this.books.filter(book => book.type === 'NonFiction').length;

        document.getElementById('totalBooks').textContent = totalBooks;
        document.getElementById('dashboardTotalBooks').textContent = totalBooks;
        document.getElementById('dashboardAvailableBooks').textContent = availableBooks;
        document.getElementById('dashboardFictionBooks').textContent = fictionBooks;
        document.getElementById('dashboardNonFictionBooks').textContent = nonFictionBooks;

        // Update recent books
        const recentBooksHtml = this.books.slice(-5).reverse().map(book => `
            <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors">
                <div>
                    <p class="font-semibold text-gray-800">${book.title}</p>
                    <p class="text-sm text-gray-600">${book.author}</p>
                </div>
                <span class="px-3 py-1 rounded-full text-xs font-semibold ${book.available ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">
                    ${book.available ? 'Available' : 'Borrowed'}
                </span>
            </div>
        `).join('');
        document.getElementById('recentBooks').innerHTML = recentBooksHtml || '<p class="text-gray-500">No books available</p>';
    }

    updateBooksTable() {
        const booksTableHtml = this.books.map(book => `
            <tr class="hover:bg-gray-50">
                <td class="px-6 py-4 text-sm font-medium text-gray-800">${book.isbn}</td>
                <td class="px-6 py-4 text-sm font-medium text-gray-800">${book.title}</td>
                <td class="px-6 py-4 text-sm text-gray-600">${book.author}</td>
                <td class="px-6 py-4 text-sm text-gray-600">${book.type}</td>
                <td class="px-6 py-4 text-sm text-gray-600">${book.category}</td>
                <td class="px-6 py-4">
                    <span class="px-3 py-1 rounded-full text-xs font-semibold ${book.available ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">
                        ${book.available ? 'Available' : 'Borrowed'}
                    </span>
                </td>
            </tr>
        `).join('');
        document.getElementById('booksTable').innerHTML = booksTableHtml || '<tr><td colspan="6" class="text-center py-8 text-gray-500">No books available</td></tr>';
    }

    updateCategories() {
        const categories = {};
        this.books.forEach(book => {
            categories[book.category] = (categories[book.category] || 0) + 1;
        });

        const categoriesHtml = Object.entries(categories).map(([category, count]) => {
            const percentage = (count / this.books.length * 100).toFixed(1);
            return `
                <div class="flex items-center justify-between">
                    <span class="text-gray-700 font-medium">${category}</span>
                    <div class="flex items-center gap-2">
                        <div class="w-32 h-2 bg-gray-200 rounded-full overflow-hidden">
                            <div class="h-full bg-gradient-to-r from-purple-500 to-blue-500 rounded-full" style="width: ${percentage}%"></div>
                        </div>
                        <span class="text-sm text-gray-600 font-medium">${count}</span>
                    </div>
                </div>
            `;
        }).join('');
        document.getElementById('categories').innerHTML = categoriesHtml || '<p class="text-gray-500">No categories available</p>';
    }

    addBook() {
        const isbn = document.getElementById('bookIsbn').value.trim();
        const title = document.getElementById('bookTitle').value.trim();
        const author = document.getElementById('bookAuthor').value.trim();
        const type = document.getElementById('bookType').value;
        const category = document.getElementById('bookCategory').value.trim();

        if (!isbn || !title || !author || !type || !category) {
            alert('Please fill in all fields');
            return;
        }

        // Check if book already exists
        if (this.books.some(book => book.isbn === isbn)) {
            alert('A book with this ISBN already exists');
            return;
        }

        const newBook = {
            isbn,
            title,
            author,
            type,
            category,
            available: true
        };

        this.books.push(newBook);
        this.updateAllViews();
        this.saveBookToFile(newBook);
        
        // Clear form
        document.getElementById('addBookForm').reset();
        
        // Show success message
        this.showSuccessModal(`Book "${title}" has been added successfully!`);
        
        // Switch to books view
        setTimeout(() => {
            window.showView('books');
        }, 1500);
    }

    async saveBookToFile(book) {
        try {
            // In a real implementation, this would call your Java backend
            console.log('Saving book to database:', book);
            // For demo purposes, we'll just log it
            const bookData = `${book.isbn},${book.title},${book.author},${book.available},${book.type},${book.category}`;
            console.log('Book data to save:', bookData);
        } catch (error) {
            console.error('Error saving book:', error);
        }
    }

    performSearch(searchTerm) {
        if (!searchTerm) {
            document.getElementById('searchResults').innerHTML = '<p class="text-gray-500 text-center">Enter a search term to find books</p>';
            return;
        }

        const results = this.books.filter(book => 
            book.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
            book.author.toLowerCase().includes(searchTerm.toLowerCase()) ||
            book.isbn.includes(searchTerm) ||
            book.category.toLowerCase().includes(searchTerm.toLowerCase())
        );

        const resultsHtml = results.map(book => `
            <div class="bg-white rounded-xl shadow-lg p-6 card-hover">
                <div class="flex items-start justify-between">
                    <div class="flex-1">
                        <h3 class="text-lg font-bold text-gray-800 mb-2">${book.title}</h3>
                        <p class="text-gray-600 mb-1"><strong>Author:</strong> ${book.author}</p>
                        <p class="text-gray-600 mb-1"><strong>ISBN:</strong> ${book.isbn}</p>
                        <p class="text-gray-600 mb-1"><strong>Type:</strong> ${book.type}</p>
                        <p class="text-gray-600 mb-2"><strong>Category:</strong> ${book.category}</p>
                        <span class="px-3 py-1 rounded-full text-xs font-semibold ${book.available ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">
                            ${book.available ? 'Available' : 'Borrowed'}
                        </span>
                    </div>
                    <div class="ml-4">
                        <i class="fas fa-book text-3xl text-purple-400"></i>
                    </div>
                </div>
            </div>
        `).join('');

        document.getElementById('searchResults').innerHTML = 
            resultsHtml || '<p class="text-gray-500 text-center">No books found matching your search</p>';
    }

    showSuccessModal(message) {
        document.getElementById('successMessage').textContent = message;
        document.getElementById('successModal').style.display = 'flex';
    }
}

// Global functions for HTML onclick handlers
window.showView = function(viewName) {
    if (window.libraryApp) {
        // Hide all views
        document.querySelectorAll('.view').forEach(view => {
            view.classList.add('hidden');
        });

        // Show selected view
        document.getElementById(viewName).classList.remove('hidden');

        // Update navigation
        document.querySelectorAll('.nav-btn').forEach(btn => {
            btn.classList.remove('border-purple-600', 'text-purple-600');
            btn.classList.add('border-transparent', 'text-gray-600');
        });

        // Find and highlight the clicked button
        if (window.event && window.event.target) {
            window.event.target.classList.remove('border-transparent', 'text-gray-600');
            window.event.target.classList.add('border-purple-600', 'text-purple-600');
        } else {
            // If no event, find the button by onclick attribute
            const targetBtn = document.querySelector(`[onclick*="showView('${viewName}')"]`);
            if (targetBtn) {
                targetBtn.classList.remove('border-transparent', 'text-gray-600');
                targetBtn.classList.add('border-purple-600', 'text-purple-600');
            }
        }

        window.libraryApp.currentView = viewName;
    }
}

window.closeModal = function() {
    document.getElementById('successModal').style.display = 'none';
}

// Initialize the app when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.libraryApp = new LibraryWebApp();
});
