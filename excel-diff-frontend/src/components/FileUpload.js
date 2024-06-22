import React, { useState } from "react";
import ExcelService from "../services/ExcelService";

const FileUpload = () => {
    const [oldFile, setOldFile] = useState(null);
    const [newFile, setNewFile] = useState(null);
    const [differences, setDifferences] = useState([]);

    const handleOldFileChanges = (e) => setOldFile(e.target.files[0]);
    const handleNewFileChanges = (e) => setNewFile(e.target.files[0]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const diffs = await ExcelService.uploadFilesAndGetDiff(oldFile, newFile);
            setDifferences(diffs);
        } catch (error) {
            console.error('Error getting the differences in the excel files', error);
        }
    };

    return (
      <div>
        <form onSubmit={handleSubmit}>
          <div>
            <label>Old Commit File:</label>
            <input type="file" onChange={handleOldFileChanges} />
          </div>
          <div>
            <label>New Commit File:</label>
            <input type="file" onChange={handleNewFileChanges} />
          </div>
          <button type="submit">Upload and Compare</button>
        </form>
        <div>
          <h2>Differences</h2>
          <ul>
            {differences.map((difference, index) => (
              <li key={index}>{difference}</li>
            ))}
          </ul>
        </div>
      </div>
    );
};

export default FileUpload;