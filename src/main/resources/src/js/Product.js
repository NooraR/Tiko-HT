import React, { Component } from "react";
import { Button, ButtonGroup, Table } from "react-bootstrap";
import "./Product.css";
import AvailabilityInfo from './AvailabilityInfo.js';


export default class Product extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showAvailability: false
        };

        this.toggleAvailability = this.toggleAvailability.bind(this);
    }

    toggleAvailability() {
        this.setState({
            showAvailability: !this.state.showAvailability
        });
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
                    <ButtonGroup
                        className="add-button"
                    >
                        <Button
                            bsStyle="warning"
                            onClick={this.toggleAvailability}
                        >
                            Katso hinnat
                        </Button>
                        <Button
                            bsStyle="primary"
                            onClick={this.props.addToCart}
                            disabled={this.props.isInShoppingCart}
                        >
                            {this.props.isInShoppingCart ? "Ostoskorissa" : "Lisää koriin"}
                        </Button>
                    </ButtonGroup>
                    <AvailabilityInfo
                        work={this.props.work}
                        visible={this.state.showAvailability}
                        toggleVisibility={this.toggleAvailability}
                    />
                </div>
            );
        } else {
            return '';
        }
    }
}