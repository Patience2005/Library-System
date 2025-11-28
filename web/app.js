// Library System JavaScript
// Student project for web development class

// book data
let books = [];
let borrowedBooks = [];
let currentUser = null;

// start the app
window.onload = function() {
    checkLogin();
    loadBooks();
    setupEvents();
    setDefaultDate();
    showView('dashboard');
};

// check if user is logged in
function checkLogin() {
    const user = sessionStorage.getItem('libraryUser');
    if (!user) {
        window.location.href = 'login.html';
        return;
    }
    currentUser = JSON.parse(user);
    document.getElementById('currentUser').textContent = currentUser.name + ' (' + currentUser.type + ')';
}

// load books from server or use sample data
async function loadBooks() {
    try {
        const response = await fetch('http://localhost:8080/api/books');
        if (response.ok) {
            books = await response.json();
            updateAll();
            return;
        }
    } catch (error) {
        console.log('Server not found, using sample data');
    }
    
    // sample books if server not available
    books = [
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
        { isbn: '978-0-13-468599-8', title: 'Here for the fun', author: 'John', type: 'NonFiction', category: 'Sasing', available: true }
    ];
    
    updateAll();
}

// setup form events
function setupEvents() {
    // add book form
    document.getElementById('addBookForm').onsubmit = function(e) {
        e.preventDefault();
        addBook();
    };
    
    // search input
    document.getElementById('searchInput').oninput = function(e) {
        searchBooks(e.target.value);
    };
    
    // borrow form
    document.getElementById('borrowForm').onsubmit = function(e) {
        e.preventDefault();
        borrowBook();
    };
    
    // catalog filters
    document.getElementById('catalogFilter').onchange = updateCatalog;
    document.getElementById('catalogSort').onchange = updateCatalog;
}

// set default due date (7 days from now)
function setDefaultDate() {
    const dueDateInput = document.getElementById('dueDate');
    if (dueDateInput) {
        const today = new Date();
        const dueDate = new Date(today);
        dueDate.setDate(today.getDate() + 7);
        dueDateInput.min = today.toISOString().split('T')[0];
        dueDateInput.value = dueDate.toISOString().split('T')[0];
    }
}

// update everything
function updateAll() {
    updateDashboard();
    updateBooksTable();
    updateCategories();
    updateCatalog();
    updateBorrowedBooks();
    updateReturnBooks();
    updateBookSelect();
}

// update dashboard
function updateDashboard() {
    const total = books.length;
    const available = books.filter(b => b.available).length;
    const fiction = books.filter(b => b.type === 'Fiction').length;
    const nonFiction = books.filter(b => b.type === 'NonFiction').length;
    
    document.getElementById('totalBooks').textContent = total;
    document.getElementById('dashboardTotalBooks').textContent = total;
    document.getElementById('dashboardAvailableBooks').textContent = available;
    document.getElementById('dashboardFictionBooks').textContent = fiction;
    document.getElementById('dashboardNonFictionBooks').textContent = nonFiction;
    
    // recent books
    const recentHtml = books.slice(-5).reverse().map(book => `
        <div class="flex justify-between p-3 bg-gray-50 rounded">
            <div>
                <p class="font-semibold">${book.title}</p>
                <p class="text-sm text-gray-600">${book.author}</p>
            </div>
            <span class="px-2 py-1 rounded text-xs ${book.available ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">
                ${book.available ? 'Available' : 'Borrowed'}
            </span>
        </div>
    `).join('');
    
    document.getElementById('recentBooks').innerHTML = recentHtml || '<p class="text-gray-500">No books</p>';
}

// update books table
function updateBooksTable() {
    const tableHtml = books.map(book => `
        <tr class="hover:bg-gray-50">
            <td class="px-6 py-3 text-sm">${book.isbn}</td>
            <td class="px-6 py-3 text-sm font-medium">${book.title}</td>
            <td class="px-6 py-3 text-sm">${book.author}</td>
            <td class="px-6 py-3 text-sm">${book.type}</td>
            <td class="px-6 py-3 text-sm">${book.category}</td>
            <td class="px-6 py-3">
                <span class="px-2 py-1 rounded text-xs ${book.available ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">
                    ${book.available ? 'Available' : 'Borrowed'}
                </span>
            </td>
        </tr>
    `).join('');
    
    document.getElementById('booksTable').innerHTML = tableHtml || '<tr><td colspan="6" class="text-center py-8 text-gray-500">No books</td></tr>';
}

// update categories
function updateCategories() {
    const categories = {};
    books.forEach(book => {
        categories[book.category] = (categories[book.category] || 0) + 1;
    });
    
    const catHtml = Object.entries(categories).map(([cat, count]) => {
        const percent = (count / books.length * 100).toFixed(1);
        return `
            <div class="flex justify-between">
                <span>${cat}</span>
                <div class="flex items-center gap-2">
                    <div class="w-24 h-2 bg-gray-200 rounded">
                        <div class="h-full bg-blue-500 rounded" style="width: ${percent}%"></div>
                    </div>
                    <span class="text-sm">${count}</span>
                </div>
            </div>
        `;
    }).join('');
    
    document.getElementById('categories').innerHTML = catHtml || '<p class="text-gray-500">No categories</p>';
}

// update catalog
function updateCatalog() {
    const filter = document.getElementById('catalogFilter').value;
    const sort = document.getElementById('catalogSort').value;
    
    let filtered = books;
    
    // filter
    if (filter) {
        filtered = filtered.filter(b => b.category === filter);
    }
    
    // sort
    filtered.sort((a, b) => {
        if (sort === 'title') return a.title.localeCompare(b.title);
        if (sort === 'author') return a.author.localeCompare(b.author);
        if (sort === 'category') return a.category.localeCompare(b.category);
        if (sort === 'available') return b.available - a.available;
        return 0;
    });
    
    // display
    const catalogHtml = filtered.map(book => `
        <div class="bg-white rounded shadow border p-4">
            <div class="flex justify-between mb-3">
                <div>
                    <h3 class="font-bold">${book.title}</h3>
                    <p class="text-sm">Author: ${book.author}</p>
                    <p class="text-sm">ISBN: ${book.isbn}</p>
                    <p class="text-sm">Category: ${book.category}</p>
                </div>
                <span class="px-2 py-1 rounded text-xs ${book.available ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">
                    ${book.available ? 'Available' : 'Borrowed'}
                </span>
            </div>
            <div class="flex justify-between">
                <span class="text-xl">📚</span>
                ${book.available ? 
                    `<button onclick="quickBorrow('${book.isbn}')" class="bg-green-500 text-white px-3 py-1 rounded text-sm">
                        📤 Quick Borrow
                    </button>` : 
                    '<span class="text-gray-500 text-sm">Not Available</span>'
                }
            </div>
        </div>
    `).join('');
    
    document.getElementById('catalogGrid').innerHTML = catalogHtml || '<p class="text-gray-500 text-center">No books found</p>';
}

// update book select dropdown
function updateBookSelect() {
    const availableBooks = books.filter(b => b.available);
    const options = availableBooks.map(book => 
        `<option value="${book.isbn}">${book.title} - ${book.author}</option>`
    ).join('');
    
    document.getElementById('bookSelect').innerHTML = '<option value="">Select an available book</option>' + options;
}

// update borrowed books
function updateBorrowedBooks() {
    const borrowedHtml = borrowedBooks.map(borrow => `
        <div class="flex justify-between p-3 bg-gray-50 rounded">
            <div>
                <p class="font-semibold">${borrow.bookTitle}</p>
                <p class="text-sm">Borrowed by: ${borrow.memberName} (${borrow.memberId})</p>
                <p class="text-sm">Due: ${borrow.dueDate}</p>
            </div>
            <span class="px-2 py-1 rounded text-xs bg-orange-100 text-orange-700">Borrowed</span>
        </div>
    `).join('');
    
    document.getElementById('borrowedBooks').innerHTML = borrowedHtml || '<p class="text-gray-500">No books borrowed</p>';
}

// update return books
function updateReturnBooks() {
    const returnHtml = borrowedBooks.map(borrow => `
        <div class="bg-white rounded shadow border p-4">
            <div class="flex justify-between">
                <div>
                    <h3 class="font-bold">${borrow.bookTitle}</h3>
                    <p class="text-sm">ISBN: ${borrow.bookIsbn}</p>
                    <p class="text-sm">Borrowed by: ${borrow.memberName} (${borrow.memberId})</p>
                    <p class="text-sm">Due Date: ${borrow.dueDate}</p>
                    <p class="text-sm">Borrowed: ${borrow.borrowDate}</p>
                </div>
                <button onclick="returnBook('${borrow.bookIsbn}')" class="bg-blue-500 text-white px-3 py-1 rounded text-sm">
                    📥 Return Book
                </button>
            </div>
        </div>
    `).join('');
    
    document.getElementById('returnBooks').innerHTML = returnHtml || '<p class="text-gray-500 text-center">No books to return</p>';
}

// add new book
function addBook() {
    const isbn = document.getElementById('bookIsbn').value.trim();
    const title = document.getElementById('bookTitle').value.trim();
    const author = document.getElementById('bookAuthor').value.trim();
    const type = document.getElementById('bookType').value;
    const category = document.getElementById('bookCategory').value.trim();
    
    if (!isbn || !title || !author || !type || !category) {
        alert('Please fill all fields');
        return;
    }
    
    if (books.some(b => b.isbn === isbn)) {
        alert('Book with this ISBN already exists');
        return;
    }
    
    const newBook = { isbn, title, author, type, category, available: true };
    books.push(newBook);
    updateAll();
    
    document.getElementById('addBookForm').reset();
    showSuccess(`Book "${title}" added successfully!`);
    
    setTimeout(() => showView('books'), 1000);
}

// search books
function searchBooks(searchTerm) {
    if (!searchTerm) {
        document.getElementById('searchResults').innerHTML = '<p class="text-gray-500 text-center">Enter search term</p>';
        return;
    }
    
    const results = books.filter(book => 
        book.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        book.author.toLowerCase().includes(searchTerm.toLowerCase()) ||
        book.isbn.includes(searchTerm) ||
        book.category.toLowerCase().includes(searchTerm.toLowerCase())
    );
    
    const resultsHtml = results.map(book => `
        <div class="bg-white rounded shadow border p-4">
            <div class="flex justify-between">
                <div>
                    <h3 class="font-bold">${book.title}</h3>
                    <p class="text-sm">Author: ${book.author}</p>
                    <p class="text-sm">ISBN: ${book.isbn}</p>
                    <p class="text-sm">Type: ${book.type}</p>
                    <p class="text-sm">Category: ${book.category}</p>
                    <span class="px-2 py-1 rounded text-xs ${book.available ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">
                        ${book.available ? 'Available' : 'Borrowed'}
                    </span>
                </div>
                <div class="text-2xl">📚</div>
            </div>
        </div>
    `).join('');
    
    document.getElementById('searchResults').innerHTML = resultsHtml || '<p class="text-gray-500 text-center">No books found</p>';
}

// borrow book
function borrowBook() {
    const memberId = document.getElementById('memberSelect').value;
    const bookIsbn = document.getElementById('bookSelect').value;
    const dueDate = document.getElementById('dueDate').value;
    
    if (!memberId || !bookIsbn || !dueDate) {
        alert('Please fill all fields');
        return;
    }
    
    const book = books.find(b => b.isbn === bookIsbn);
    if (!book || !book.available) {
        alert('Book not available');
        return;
    }
    
    const memberSelect = document.getElementById('memberSelect');
    const memberName = memberSelect.options[memberSelect.selectedIndex].text.split(' (')[0];
    
    const borrowRecord = {
        bookIsbn: book.isbn,
        bookTitle: book.title,
        memberId: memberId,
        memberName: memberName,
        borrowDate: new Date().toISOString().split('T')[0],
        dueDate: dueDate
    };
    
    book.available = false;
    borrowedBooks.push(borrowRecord);
    updateAll();
    
    document.getElementById('borrowForm').reset();
    setDefaultDate();
    
    showSuccess(`Book "${book.title}" borrowed by ${memberName}!`);
}

// quick borrow from catalog
function quickBorrow(bookIsbn) {
    const book = books.find(b => b.isbn === bookIsbn);
    if (!book || !book.available) {
        alert('Book not available');
        return;
    }
    
    showView('borrow');
    document.getElementById('bookSelect').value = bookIsbn;
}

// return book
function returnBook(bookIsbn) {
    const borrowIndex = borrowedBooks.findIndex(b => b.bookIsbn === bookIsbn);
    if (borrowIndex === -1) {
        alert('Borrow record not found');
        return;
    }
    
    const borrow = borrowedBooks[borrowIndex];
    const book = books.find(b => b.isbn === bookIsbn);
    if (book) {
        book.available = true;
    }
    
    borrowedBooks.splice(borrowIndex, 1);
    updateAll();
    
    showSuccess(`Book "${borrow.bookTitle}" returned successfully!`);
}

// show success message
function showSuccess(message) {
    document.getElementById('successMessage').textContent = message;
    document.getElementById('successModal').style.display = 'flex';
}

// switch views
function showView(viewName) {
    // hide all views
    document.querySelectorAll('.view').forEach(view => {
        view.classList.add('hidden');
    });
    
    // show selected view
    document.getElementById(viewName).classList.remove('hidden');
    
    // update navigation
    document.querySelectorAll('.nav-btn').forEach(btn => {
        btn.classList.remove('bg-blue-50', 'text-blue-600');
        btn.classList.add('text-gray-600');
    });
    
    // highlight active button
    const activeBtn = document.querySelector(`[onclick*="showView('${viewName}')"]`);
    if (activeBtn) {
        activeBtn.classList.add('bg-blue-50', 'text-blue-600');
        activeBtn.classList.remove('text-gray-600');
    }
}

// global functions for HTML
window.showView = showView;
window.quickBorrow = quickBorrow;
window.returnBook = returnBook;

function closeModal() {
    document.getElementById('successModal').style.display = 'none';
}

function logout() {
    sessionStorage.removeItem('libraryUser');
    window.location.href = 'login.html';
}

window.closeModal = closeModal;
window.logout = logout;
