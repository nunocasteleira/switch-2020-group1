import axios from 'axios';

export function retrieveRelationships(familyId, token) {
    return axios.get(`http://localhost:8080/families/${familyId}/relationships`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }})
};

export function createRelationship(state, familyId, token) {
    return axios.post(`http://localhost:8080/families/${familyId}/relationships`, state, {
        headers: {
            'Authorization': `Bearer ${token}`
        }})
};

export function changeRelationship(state, familyId, relationshipId, token) {
    let form_data = new FormData();
    form_data.append('relationshipType', state.relationshipType);

    return axios.put(`http://localhost:8080/families/${familyId}/relationships/${relationshipId}`, form_data, {
        headers: {
            'Authorization': `Bearer ${token}`
        }})
};

export function getRelationshipTypes(token) {
    return axios.get(`http://localhost:8080/families/relationshipTypes`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }})
};