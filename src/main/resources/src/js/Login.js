import React, { Component } from "react";
import { Modal, Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import { Link, Redirect } from 'react-router-dom';
import "./Login.css";

export default class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            modalIsOpen: true,
            email: "",
            password: "",
            redirect: false
        };

        this.toggleModal = this.toggleModal.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    toggleModal() {
        this.setState({
            modalIsOpen: !this.state.modalIsOpen,
            redirect: !this.state.redirect
        });
    }

    validateForm() {
        return this.emailValidation() && this.passwordValidation();
    }

    handleChange(event) {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    handleSubmit(event) {
        event.preventDefault();
        fetch("/login", {
            method: 'post',
            headers: {"Content-type": "application/json; charset=UTF-8"},
            body: {
                "email": this.state.email,
                "password": this.state.password
            }
        })
        .then((data) => {
            console.log(data);
        });
    }

    emailValidation() {
        return this.state.email.length > 0;
    }

    passwordValidation() {
        return this.state.password.length > 0;
    }

    render() {
        return (
            <div className="login">
                {this.state.redirect && <Redirect to="/" />}
                <Modal
                    show={this.state.modalIsOpen}
                    onHide={this.toggleModal}
                >
                    <form onSubmit={this.handleSubmit}>
                        <Modal.Header closeButton>
                            <Modal.Title>Kirjaudu sisään</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <FormGroup controlId="email" bsSize="large" validationState={this.emailValidation() ? 'success' : 'error'}>
                                <ControlLabel>Sähköpostiosoite</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="email"
                                    value={this.state.email}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup controlId="password" bsSize="large" validationState={this.passwordValidation() ? 'success' : 'error'}>
                                <ControlLabel>Salasana</ControlLabel>
                                <FormControl
                                    value={this.state.password}
                                    onChange={this.handleChange}
                                    type="password"
                                />
                            </FormGroup>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button
                                disabled={!this.validateForm()}
                                type="submit"
                            >
                                Kirjaudu
                            </Button>
                        </Modal.Footer>
                    </form>
                </Modal>
            </div>
        );
    }
}