import React, { Component } from "react";
import { Button, Modal, Table, Panel } from 'react-bootstrap';
import "./Order.css";

export default class Order extends Component {

    constructor(props) {
        super(props);

        this.cancelOrder = this.cancelOrder.bind(this);
        this.confirmOrder = this.confirmOrder.bind(this);
    }

    cancelOrder() {
        fetch("/order/cancel", {
            method: 'GET',
            credentials: "same-origin"
        }).then(results => {
            return results.json();
        })
        .then(json => {
            this.props.toggleVisibility();
            this.props.showConfirmation(true, "Tilaus peruttu", "Tilauksesi peruutettiin onnistuneesti.");
        });
    }

    confirmOrder() {
        fetch("/order/confirm", {
            method: 'GET',
            credentials: "same-origin"
        }).then(results => {
            return results.json();
        })
        .then(json => {
            this.props.toggleVisibility();
            this.props.refreshWorks();
            this.props.clearShoppingCart();
            this.props.showConfirmation(true, "Tilaus vahvistettu", "Kiitos tilauksestasi! Tilaus odottaa myyjän käsittelyä ja lähetetään teille pian.");
        });
    }

    render() {
        if(this.props.visible && this.props.data) {
            let data = this.props.data;
            let totalPrice = 0;
            data.products = data.products.map((item) => {
                totalPrice += item.sellingPrice;

                item.work = data.works.find(work => {
                    return item.workid === work.id;
                });

                return item;
            });

            return (
                <Modal
                    show={this.props.visible}
                    onHide={() => this.cancelOrder()}
                    className="Order"
                >
                    <Modal.Header>
                        <Modal.Title>Tuotteet varattu tilaukselle #{data.id}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Table responsive striped bordered>
                            <tbody>
                                <tr><td>Tilausnumero</td><td>#{data.id}</td></tr>
                                <tr><td>Tilauspäivämäärä</td><td>{data.orderDate}</td></tr>
                                <tr><td>Tila</td><td>Odottaa vahvistusta</td></tr>
                                <tr><td>Postituskulut</td><td>{data.postage.toFixed(2)} €</td></tr>
                                <tr><td>Kokonaissumma</td><td>{(totalPrice + data.postage).toFixed(2)} €</td></tr>
                            </tbody>
                        </Table>
                        <h2>Tuotteet</h2>
                        {data.products.map((item, i) => {
                            return (
                                <Panel key={i}>
                                    <Panel.Heading>
                                        <Panel.Title toggle>{item.work.name}, {item.work.author}</Panel.Title>
                                    </Panel.Heading>
                                    <Panel.Collapse>
                                        <Panel.Body>
                                            <ul>
                                                {item.work.published && <li>{item.work.published}</li>}
                                                {item.work.genre && <li>{item.work.genre}</li>}
                                                {item.work.type && <li> {item.work.type}</li>}
                                                <li>paino {item.work.weight} kg</li>
                                            </ul>
                                            <h3>Myyjä</h3>
                                            <ul>
                                                <li>{item.antiquary.name}</li>
                                                <li>{item.antiquary.address}</li>
                                                <li>{item.antiquary.web}</li>
                                            </ul>
                                        </Panel.Body>
                                    </Panel.Collapse>
                                    <Panel.Footer>Hinta {item.sellingPrice} €</Panel.Footer>
                                </Panel>
                            );
                        })}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button
                            bsStyle="danger"
                            onClick={() => this.cancelOrder()}
                        >
                            Peruuta
                        </Button>
                        <Button
                            bsStyle="primary"
                            onClick={() => this.confirmOrder()}
                        >
                            Vahvista
                        </Button>
                    </Modal.Footer>
                </Modal>
            );
        } else {
            return '';
        }
    }
}