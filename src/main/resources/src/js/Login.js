import React, { Component } from "react";
import { Modal, Button, ButtonToolbar, FormGroup, FormControl, ControlLabel, Alert } from "react-bootstrap";

export default class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            email: "",
            password: "",
            showAlert: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
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
            method: 'POST',
            headers: {"Content-type": "application/json; charset=UTF-8"},
            body: JSON.stringify({
                "email": this.state.email,
                "password": this.state.password
            }),
            credentials: "same-origin"
        })
        .then(result => {
            return result.json();
        })
        .then(json => {
            if(json.success) {
                this.props.setUser(true, json.data);
                this.props.toggleModal();
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

    emailValidation() {
        return this.state.email.length > 0;
    }

    passwordValidation() {
        return this.state.password.length > 0;
    }

    render() {
        return (
            <div className="login">
                <Modal
                    show={this.props.showModal}
                    onHide={this.props.toggleModal}
                >
                    <form onSubmit={this.handleSubmit}>
                        <Modal.Header closeButton>
                            <Modal.Title>Kirjaudu sisään</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <FormGroup controlId="email" bsSize="large" validationState={this.emailValidation() ? null : 'error'}>
                                <ControlLabel>Sähköpostiosoite</ControlLabel>
                                <FormControl
                                    autoFocus
                                    type="email"
                                    value={this.state.email}
                                    onChange={this.handleChange}
                                />
                            </FormGroup>
                            <FormGroup controlId="password" bsSize="large" validationState={this.passwordValidation() ? null : 'error'}>
                                <ControlLabel>Salasana</ControlLabel>
                                <FormControl
                                    value={this.state.password}
                                    onChange={this.handleChange}
                                    type="password"
                                />
                            </FormGroup>
                            {this.state.showAlert &&
                            <Alert bsStyle="danger">
                                <strong>
                                    Wrong username or password
                                </strong>
                            </Alert>}
                        </Modal.Body>
                        <Modal.Footer>
                            <ButtonToolbar>
                                <Button
                                    disabled={!this.validateForm()}
                                    bsStyle="success"
                                    type="submit"
                                >
                                    Kirjaudu
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