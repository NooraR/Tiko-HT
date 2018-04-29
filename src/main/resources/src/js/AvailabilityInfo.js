import React, { Component } from "react";
import {Button, Modal} from "react-bootstrap";
import "./AvailabilityInfo.css";

export default class Product extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        if(this.props.visible) {
            let products = this.props.work.products;
            let antiquaries = [];

            products.sort((a, b) => {
                return a.sellingPrice <= b.sellingPrice;
            });

            products.forEach((item) => {
                if (!antiquaries[item.antiquary.id]) {
                    antiquaries[item.antiquary.id] = item.antiquary;
                    antiquaries[item.antiquary.id].prices = [];
                }
                antiquaries[item.antiquary.id].prices.push(item.sellingPrice);
            });

            return(
                <Modal
                    show={this.props.visible}
                    onHide={this.props.toggleVisibility}
                >
                    <Modal.Header closeButton>
                        <Modal.Title>Hinnat ja saatavuus</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {antiquaries.map((seller) => {
                            return(
                                <dl>
                                    <dt>{seller.name}, {seller.address}</dt>
                                    {seller.prices.map((price) => {
                                        return <dd>{price} €</dd>;
                                    })}
                                </dl>
                            );
                        })}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button
                            bsStyle="primary"
                            onClick={this.props.toggleVisibility}
                        >OK</Button>
                    </Modal.Footer>
                </Modal>
            );
        } else {
            return "";
        }
    }
}