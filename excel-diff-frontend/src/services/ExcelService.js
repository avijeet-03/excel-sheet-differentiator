import axios from "axios";

const BASE_URL = 'http://localhost:8080/api/excel';

const uploadFilesAndGetDiff = async (oldFile, newFile) => {
    const formData = new FormData();
    formData.append('oldCommitFile', oldFile);
    formData.append('newCommitFile', newFile);

    const headers = {
        'Content-Type': 'multipart/form-data',
    };

    try {
        const response = await axios.post(`${BASE_URL}/diff`, formData, {headers});
        return response.data;
    } catch (error) {
        console.error('Error uploading the excel files', error);
        throw error;
    }
};

export default {
    uploadFilesAndGetDiff,
};