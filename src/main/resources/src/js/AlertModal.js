import React, { Component } from "react";
import { Button, ButtonToolbar, Modal } from "react-bootstrap";

export default class AlertModal extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Modal
                show={this.props.show}
                onHide={this.props.toggleModal}
            >
                <Modal.Header closeButton>
                    <Modal.Title>
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.props.body}
                </Modal.Body>
                <Modal.Footer>
                    <ButtonToolbar>
                        <Button
                            onClick={this.props.toggleModal}
                            bsStyle="primary"
                        >
                            OK
                        </Button>
                    </ButtonToolbar>
                </Modal.Footer>
            </Modal>
        );
    }
}