import React, { Component } from "react";
import AddProduct from './AddProduct';
import "./Maintenance.css";

export default class Maintenance extends Component {
    render() {
        return (
            <div className="container">
                <AddProduct />
                <hr />
            </div>
        );
    }
}