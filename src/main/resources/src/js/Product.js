import React, { Component } from "react";
import { Button, Table } from "react-bootstrap";
import "./Product.css";

export default class Product extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        if(this.props.work) {
            let {
                author,
                name,
                isbn,
                published,
                genre,
                type,
                weight,
                products,
                amount
            } = this.props.work;

            return (
                <div className="product">
                    <h1>{name}</h1>
                    <h2>{author}</h2>
                    <Table responsive>
                        <tbody>
                            <tr><td>Julkaisuvuosi</td><td>{published}</td></tr>
                            <tr><td>Genre</td><td>{genre}</td></tr>
                            <tr><td>Tyyppi</td><td>{type}</td></tr>
                            <tr><td>Paino</td><td>{weight} kg</td></tr>
                            {isbn && <tr><td>ISBN</td><td>{isbn}</td></tr>}
                            <tr><td>Saatavilla</td><td>{products.length}</td></tr>
                        </tbody>
                    </Table>
                    <Button
                        bsStyle="primary"
                        className="add-button"
                        onClick={this.props.addToCart}
                    >
                        Lisää koriin
                    </Button>
                </div>
            );
        } else {
            return '';
        }
    }
}