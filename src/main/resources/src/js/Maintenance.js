import React, { Component } from "react";
import AddProduct from './AddProduct';
import Reports from './Reports';
import "./Maintenance.css";

export default class Maintenance extends Component {
    render() {
        return (
            <div className="container">
                <AddProduct />
                <Reports />
            </div>
        );
    }
}