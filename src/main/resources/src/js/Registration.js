import React, { Component } from "react";
import { Modal, Button, ButtonToolbar, FormGroup, FormControl, ControlLabel, Alert } from "react-bootstrap";
import AlertModal from "./AlertModal.js";

export default class Registration extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showAlert: false,
            showConfirmation: false,
            firstName: "",
            lastName: "",
            address: "",
            phoneNumber: "",
            email: "",
            password: ""
        };
        this.toggleConfirmation = this.toggleConfirmation.bind(this);
    }

    validateForm() {
        return this.state.firstName.length > 0
            && this.state.lastName.length > 0
            && this.state.address.length > 0
            && this.state.email.length > 0
            && this.state.password.length > 0;
    }

    validateField = value => {
        return value.length > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    toggleConfirmation() {
        this.setState({
            showConfirmation: !this.state.showConfirmation
        });
    }

    handleSubmit = event => {
        event.preventDefault();
        fetch("/register", {
            method: 'POST',
            headers: {"Content-type": "application/json; charset=UTF-8"},
            body: JSON.stringify(this.state),
            credentials: "same-origin"
        })
            .then(result => {
                console.log(result);
                return result.json();
            })
            .then(json => {
                if(json.success) {
                    this.props.toggleModal();
                    this.toggleConfirmation();
                } else {
                    this.setState({
                        showAlert: true
                    });
                    setTimeout(() => {
                        this.setState({
                            showAlert: false
                        });
                    }, 5000)
                }
            });
    }

    render() {
        return (
            <div className="Registration">
                <AlertModal
                    show={this.state.showConfirmation}
                    toggleModal={this.toggleConfirmation}
                    header="Kiitos rekisteröitymisestäsi"
                    body="Rekisteröidyit onnistuneesti, voit nyt kirjautua sisään käyttäjälläsi."
                />
                <Modal
                    show={this.props.showModal}
                    onHide={this.props.toggleModal}
                >
                    <form onSubmit={this.handleSubmit}>
                        <Modal.Header closeButton>
                            <Modal.Title>Rekisteröidy</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            {this.state.showAlert &&
                                <Alert bsStyle="danger">
                                    <strong>
                                        Failed to register, please try again
                                    </strong>
                                </Alert>}
                            <FormGroup
                                controlId="firstName"
                                bsSize="large"
                                validationState={this.validateField(this.state.firstName) ? null : 'error'}
                            >
                                <ControlLabel>Etunimi*</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="text"
                                    value={this.state.firstName}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup
                                controlId="lastName"
                                bsSize="large"
                                validationState={this.validateField(this.state.lastName) ? null : 'error'}
                            >
                                <ControlLabel>Sukunimi*</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="text"
                                    value={this.state.lastName}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup
                                controlId="address"
                                bsSize="large"
                                validationState={this.validateField(this.state.address) ? null : 'error'}
                            >
                                <ControlLabel>Osoite*</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="text"
                                    value={this.state.address}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup
                                controlId="phoneNumber"
                                bsSize="large"
                            >
                                <ControlLabel>Puhelinnumero</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="text"
                                    value={this.state.phoneNumber}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup
                                controlId="email"
                                bsSize="large"
                                validationState={this.validateField(this.state.email) ? null : 'error'}
                            >
                                <ControlLabel>Sähköpostiosoite*</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="email"
                                    value={this.state.email}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup
                                controlId="password"
                                bsSize="large"
                                validationState={this.validateField(this.state.password) ? null : 'error'}
                            >
                                <ControlLabel>Salasana*</ControlLabel>
                                <FormControl
                                    value={this.state.password}
                                    onChange={this.handleChange}
                                    type="password"
                                />
                            </FormGroup>
                            <p>*:llä merkityt kohdat ovat pakollisia</p>
                        </Modal.Body>
                        <Modal.Footer>
                            <ButtonToolbar>
                                <Button
                                    disabled={!this.validateForm()}
                                    type="submit"
                                    bsStyle="success"
                                >
                                    Rekisteröidy
                                </Button>
                                <Button
                                    bsStyle="danger"
                                    onClick={this.props.toggleModal}
                                >
                                    Peruuta
                                </Button>
                            </ButtonToolbar>
                        </Modal.Footer>
                    </form>
                </Modal>
            </div>
        );
    }
}