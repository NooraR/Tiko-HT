import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import "./Search.css";

export default class Search extends Component {
    constructor(props) {
        super(props);

        this.state = {
            search: ""
        };
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
            <div className="Search">
                <form onSubmit={this.handleSubmit}>
                    <FormGroup bsSize="large">
                        <FormControl
                            autoFocus
                            placeholder="Search.."
                            type="text"
                            value={this.state.search}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                </form>
            </div>
        );
    }


}