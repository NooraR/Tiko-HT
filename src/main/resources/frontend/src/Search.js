import React, { Component } from "react";
import { Button, FormGroup, FormControl } from "react-bootstrap";
import Product from './Product';
import "./Search.css";

export default class Search extends Component {
    constructor() {
        super();
        this.state = {
            works: [],
        };
    }

    componentDidMount() {
        fetch('/api/data/works')
            .then(results => {
                return results.json();
            }).then(data => {
            let works = data.data;
            this.setState({works: works});
            console.log("state", this.state.works);
        }
        )
    }

    handleClick(id) {
        //this.state.works.forEach(result => {

        //}
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
            <div className="SearchContainer">
                <div className="Search">
                    <form onSubmit={this.handleSubmit}>
                        <FormGroup className="FormGroup" bsSize="large">
                            <FormControl
                                autoFocus
                                placeholder="Search.."
                                type="text"
                                value={this.state.search}
                                onChange={this.handleChange}
                            />
                        </FormGroup>
                        <Button
                            block
                            bsSize="large"
                            type="submit"
                        >
                            Hae
                        </Button>
                    </form>
                </div>
                <div className="ResultContainer">
                    {
                        this.state.works.forEach(result => {
                            console.log({name: result.name}, {id: result.id}, {author: result.author});
                            return(
                                <Product id={result.id} author={result.author} name={result.name} isbn={result.isbn} published={result.published} genre={result.genre} type={result.type} weight={result.weight} products={result.products}/>
                            )
                        })
                    }
                </div>
            </div>
        );
    }
}