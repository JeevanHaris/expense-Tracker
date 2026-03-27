const BASE_URL = 'http://localhost:8080/api';

const api = {
  async request(method, path, body = null) {
    const opts = {
      method,
      headers: { 'Content-Type': 'application/json' }
    };
    if (body) opts.body = JSON.stringify(body);
    const res = await fetch(BASE_URL + path, opts);
    if (!res.ok) {
      const err = await res.json().catch(() => ({ error: 'Request failed' }));
      throw new Error(err.error || err.message || 'Request failed');
    }
    if (res.status === 204) return null;
    return res.json();
  },

  // Expenses
  getExpenses: (params = {}) => {
    const q = new URLSearchParams(params).toString();
    return api.request('GET', `/expenses${q ? '?' + q : ''}`);
  },
  getExpense: (id) => api.request('GET', `/expenses/${id}`),
  createExpense: (data) => api.request('POST', '/expenses', data),
  updateExpense: (id, data) => api.request('PUT', `/expenses/${id}`, data),
  deleteExpense: (id) => api.request('DELETE', `/expenses/${id}`),
  getSummary: () => api.request('GET', '/expenses/summary'),

  // Categories
  getCategories: () => api.request('GET', '/categories'),
  createCategory: (data) => api.request('POST', '/categories', data),
  updateCategory: (id, data) => api.request('PUT', `/categories/${id}`, data),
  deleteCategory: (id) => api.request('DELETE', `/categories/${id}`)
};

window.api = api;
