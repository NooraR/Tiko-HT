import React, { Component } from "react";
import { Button, Panel } from 'react-bootstrap';


export default class CartListing extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        let item = this.props.item;
        item.products.sort((a, b) => {
            return a.sellingPrice <= b.sellingPrice;
        });
        return(
            <Panel>
                <Panel.Heading>
                    <Panel.Title>{item.name}, {item.author}</Panel.Title>
                </Panel.Heading>
                <Panel.Body>
                    <ul>
                        <li>Julkaistu {item.published}</li>
                        {item.genre && <li>{item.genre}</li>}
                        {item.type && <li> {item.type}</li>}
                        <li>paino {item.weight} kg</li>
                    </ul>
                    <div className="AmountSelection">
                        Määrä
                        <Button
                            onClick={this.props.decreaseAmount}
                        >-</Button>
                        {item.amount}
                        <Button
                            bsStyle="primary"
                            onClick={this.props.increaseAmount}
                        >+</Button>
                        <Button
                            bsStyle="danger"
                            onClick={this.props.removeFromCart}
                        >Poista</Button>
                    </div>
                </Panel.Body>
            </Panel>
        );
    }
}