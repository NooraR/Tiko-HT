import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import "./Registration.css";

export default class Registration extends Component {
    constructor(props) {
        super(props);

        this.state = {
            firstName: "",
            lastName: "",
            address: "",
            phoneNumber: "",
            email: "",
            password: ""
        };
    }

    validateForm() {
        return this.state.firstName.length > 0
            && this.state.lastName.length > 0
            && this.state.address.length > 0
            && this.state.phoneNumber.length > 0
            && this.state.email.length > 0
            && this.state.password.length > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    handleSubmit = event => {
        event.preventDefault();
    }

    render() {
        return (
            <div className="Overlay">
                <div className="Popup">
                    <div className="close">
                        <a href="#" className="close">✖</a>
                    </div>
                    <div className="Registration">
                        <form onSubmit={this.handleSubmit}>
                            <FormGroup controlId="firstName" bsSize="large">
                                <ControlLabel>Etunimi*</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="text"
                                    value={this.state.firstName}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup controlId="lastName" bsSize="large">
                                <ControlLabel>Sukunimi*</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="text"
                                    value={this.state.lastName}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup controlId="address" bsSize="large">
                                <ControlLabel>Osoite*</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="text"
                                    value={this.state.address}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup controlId="phoneNumber" bsSize="large">
                                <ControlLabel>Puhelinnumero*</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="text"
                                    value={this.state.phoneNumber}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup controlId="email" bsSize="large">
                                <ControlLabel>Sähköpostiosoite*</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="email"
                                    value={this.state.email}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup controlId="password" bsSize="large">
                                <ControlLabel>Salasana*</ControlLabel>
                                <FormControl
                                    value={this.state.password}
                                    onChange={this.handleChange}
                                    type="password"
                                />
                            </FormGroup>
                            <p>*:llä merkityt kohdat ovat pakollisia</p>
                            <Button
                                block
                                bsSize="large"
                                disabled={!this.validateForm()}
                                type="submit"
                            >
                                Rekisteröidy
                            </Button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}