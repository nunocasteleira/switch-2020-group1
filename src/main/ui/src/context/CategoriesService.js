import axios from 'axios';

export function getStandardCategories(token){
    return axios.get(`http://localhost:8080/categories/standard/tree`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }})
}