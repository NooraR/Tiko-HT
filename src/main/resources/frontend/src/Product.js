import React, { Component } from "react";
import "./Product.css";

export default class Product extends Component {
    constructor(props) {
        super(props);

        this.state = {
            name: "",
            auchtor: "",
            isbn: "",
            published: "",
            genre: "",
            type: "",
            weight: ""
        };
    }

    getProduct() {

    }


    render() {
        return (
            <div className="Product">

                <p>Nimi: {this.props.name}</p>
                <p>Nimi: {this.props.name}</p>
            </div>
        );
    }
}