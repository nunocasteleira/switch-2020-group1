import axios from 'axios';

export function createPersonalCashAccount(state, userId) {
    return axios.post(`http://localhost:8080/accounts/cash/${userId}`, state)
};

export function createBankAccount(state, userId) {
    return axios.post(`http://localhost:8080/accounts/${userId}`, state)
};