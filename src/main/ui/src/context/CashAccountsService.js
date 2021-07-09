import axios from 'axios';

export function getCashAccounts(userId){
    return axios.get(`http://localhost:8080/members/${userId}/cashAccounts`, {
        })
};

export function getMembers(userId, token){
    return axios.get(`http://localhost:8080/families/${userId}/membersAccounts`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }})
};

export function transferMoney(state, originAccountId) {
    return axios.post(`http://localhost:8080/transactions/${originAccountId}/transfers`, state)
};

export function registerPayment(state, originAccountId) {
    return axios.post(`http://localhost:8080/transactions/${originAccountId}/payments`, state)
};

export function getFamilyCategories(familyId){
    return axios.get(`http://localhost:8080/categories/all/list/${familyId}`, {
    })
};

export function getCategoriesList(){
    return axios.get(`http://localhost:8080/categories/standard/list`, {
    })
};

