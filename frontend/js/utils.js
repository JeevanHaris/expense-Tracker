// Format currency in INR
function formatCurrency(amount) {
  return '₹' + Number(amount || 0).toLocaleString('en-IN', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
}

// Format date nicely
function formatDate(dateStr) {
  const d = new Date(dateStr + 'T00:00:00');
  return d.toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
}

// Toast notifications
let toastContainer;
function getToastContainer() {
  if (!toastContainer) {
    toastContainer = document.createElement('div');
    toastContainer.className = 'toast-container';
    document.body.appendChild(toastContainer);
  }
  return toastContainer;
}

function showToast(message, type = 'success') {
  const icon = type === 'success' ? '✓' : '✕';
  const toast = document.createElement('div');
  toast.className = `toast ${type}`;
  toast.innerHTML = `<span>${icon}</span><span>${message}</span>`;
  getToastContainer().appendChild(toast);
  setTimeout(() => {
    toast.style.animation = 'toastOut 0.3s ease forwards';
    setTimeout(() => toast.remove(), 300);
  }, 3000);
}

// Set active nav link
function setActiveNav() {
  const page = location.pathname.split('/').pop() || 'index.html';
  document.querySelectorAll('.nav-link').forEach(link => {
    const href = link.getAttribute('href');
    link.classList.toggle('active', href === page || (page === '' && href === 'index.html'));
  });
}

// Hex to rgba
function hexToRgba(hex, alpha) {
  const r = parseInt(hex.slice(1,3),16);
  const g = parseInt(hex.slice(3,5),16);
  const b = parseInt(hex.slice(5,7),16);
  return `rgba(${r},${g},${b},${alpha})`;
}

// Category badge HTML
function catBadge(name, color) {
  const bg = hexToRgba(color || '#888', 0.15);
  return `<span class="cat-badge" style="background:${bg};color:${color || '#888'}">
    <span class="cat-dot" style="background:${color || '#888'}"></span>${name}
  </span>`;
}

// Payment method label
function paymentLabel(method) {
  const map = { CASH: '💵 Cash', CARD: '💳 Card', UPI: '📱 UPI', BANK_TRANSFER: '🏦 Bank', OTHER: '• Other' };
  return map[method] || method;
}

window.formatCurrency = formatCurrency;
window.formatDate = formatDate;
window.showToast = showToast;
window.setActiveNav = setActiveNav;
window.hexToRgba = hexToRgba;
window.catBadge = catBadge;
window.paymentLabel = paymentLabel;
