import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import Modal from 'react-modal';
import { HashRouter, Route } from 'react-router-dom';
import Registration from './Registration';
import { Link } from 'react-router-dom';
import "./Login.css";

export default class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            modalIsOpen: true,
            email: "",
            password: ""
        };

        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }

    openModal() {
        this.setState({modalIsOpen: true});
    }

    closeModal() {
        this.setState({modalIsOpen: false});
    }

    validateForm() {
        return this.state.email.length > 0 && this.state.password.length > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    handleSubmit = event => {
        event.preventDefault();
    }

    emailValidation() {
        const length = this.state.email.length;
        if (length > 5) return 'success';
        return 'error';
    }

    passwordValidation() {
        const length = this.state.email.length;
        if (length > 10) return 'success';
        else if (length > 5) return 'warning';
        return 'error';
    }

    render() {
        return (
            <HashRouter>
            <div className="login">
                <Modal
                    isOpen={this.state.modalIsOpen}
                    onRequestClose={this.closeModal}
                    contentLabel="Example Modal"
                >
                    <button className="close" onClick={this.closeModal}>close</button>
                    <h2 ref={subtitle => this.subtitle = subtitle}>Sisäänkirjautuminen</h2>
                    <form onSubmit={this.handleSubmit}>
                        <FormGroup controlId="email" bsSize="large" validationState={this.emailValidation()}>
                            <ControlLabel>Sähköpostiosoite</ControlLabel>
                            <FormControl
                                autoFocus
                                type="email"
                                value={this.state.email}
                                onChange={this.handleChange}
                            />
                        </FormGroup>
                        <FormGroup controlId="password" bsSize="large" validationState={this.passwordValidation()}>
                            <ControlLabel>Salasana</ControlLabel>
                            <FormControl
                                value={this.state.password}
                                onChange={this.handleChange}
                                type="password"
                            />
                        </FormGroup>
                        <Button
                            block
                            bsSize="large"
                            disabled={!this.validateForm()}
                            type="submit"
                        >
                            <Link className="link" to="/registration">Rekisteröidy</Link>
                        </Button>
                        <Button
                            block
                            bsSize="large"
                            disabled={!this.validateForm()}
                            type="submit"
                        >
                            Kirjaudu
                        </Button>
                    </form>
                </Modal>
                <div>
                    <Route path="/registration" component={Registration}/>
                </div>
            </div>
            </HashRouter>
        );
    }
}