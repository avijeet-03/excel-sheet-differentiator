import React from "react";
import '../styles/DiffViewer.css'

const DiffViewer = ({ data }) => {
    return (
        <div className="diff-viewer">
        <table>
            <tbody>
            {data.map((item, index) => (
                <tr key={index} className={item.type.toLowerCase()}>
                <td>{item.lineNo}</td>
                {item.content.map((values, index) => {
                    return <td key={index}>{values}</td>
                })}
                </tr>
            ))}
            </tbody>
        </table>
        </div>
    );
};

export default DiffViewer;