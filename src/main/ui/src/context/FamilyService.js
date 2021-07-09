import axios from 'axios';

export function createFamilyAndAdministrator(data, token) {

    try {
        const addFamily = axios.post(`http://localhost:8080/families`, data, {
            headers: {
                'Authorization': `Bearer ${token}`
            }})
        return addFamily;
    } catch (error) {
        throw new Error(error);
    }
};

export function createFamilyMember(data, familyId, token) {
    try {
        const addMember = axios.post(`http://localhost:8080/families/${familyId}/members`, data, {
            headers: {
                'Authorization': `Bearer ${token}`
            }})
        return addMember;
    } catch (error) {
        throw new Error(error);
    }
}
export function getFamilyMembers(familyId, token){
    return axios.get(`http://localhost:8080/families/${familyId}/members`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }})
};