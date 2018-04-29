import React, { Component } from "react";
import { Button, Panel } from 'react-bootstrap';
import AvailabilityInfo from './AvailabilityInfo.js';


export default class CartListing extends Component {
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
                        {item.published && <li>{item.published}</li>}
                        {item.genre && <li>{item.genre}</li>}
                        {item.type && <li> {item.type}</li>}
                        <li>paino {item.weight} kg</li>
                    </ul>
                    <div className="AmountSelection">
                        Määrä
                        <Button
                            onClick={this.props.decreaseAmount}
                            disabled={item.amount === 1}
                        >-</Button>
                        {item.amount}
                        <Button
                            bsStyle="primary"
                            onClick={this.props.increaseAmount}
                            disabled={item.amount === item.products.length}
                        >+</Button>
                        <Button
                            bsStyle="danger"
                            onClick={this.props.removeFromCart}
                        >Poista</Button>
                        <Button
                            bsStyle="warning"
                            onClick={this.toggleAvailability}
                        >
                            Katso hinnat
                        </Button>
                        <AvailabilityInfo
                            work={this.props.item}
                            toggleVisibility={this.toggleAvailability}
                            visible={this.state.showAvailability}
                        />
                    </div>
                </Panel.Body>
            </Panel>
        );
    }
}