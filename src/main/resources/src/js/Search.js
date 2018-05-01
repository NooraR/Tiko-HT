import React, { Component } from "react";
import Product from './Product';
import "./Search.css";

export default class Search extends Component {
    constructor(props) {
        super(props);

        this.state = {
            search: '',
        };

        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        this.props.fetchWorks();
    }

    handleChange = (e) => {
        this.setState({ search: e.target.value });
    };

    render() {
        if(this.props.show) {
            let search = this.state.search;
            let works = this.props.works;

            if (search && works) {
                works = works.filter(function (i) {
                    return i.name.toLowerCase().match(search.toLowerCase())
                        || i.author.toLowerCase().match(search.toLowerCase())
                        || (i.isbn) && i.isbn.match(search.toString())
                        || (i.published) && i.published.toString().match(search.toString())
                        || (i.genre) && i.genre.toLowerCase().match(search.toLowerCase())
                        || (i.type) && i.type.toLowerCase().match(search.toLowerCase())
                });
            }

            return (
                <div className="SearchContainer">
                    <div className="Search">
                        <input type="text" value={this.state.search} onChange={this.handleChange}
                               placeholder="Hae"/>
                    </div>
                    <div className="ResultContainer">
                        { works.length !== 0 ?
                            works.map((work, i) => {
                                return (
                                    <Product
                                        key={i}
                                        work={work}
                                        addToCart={() => this.props.addToCart(work)}
                                        isInShoppingCart={false}
                                    />
                                )
                            })
                        :
                            <p className="no-products">Ei tuotteita saatavilla</p>
                        }
                    </div>
                </div>
            );
        } else {
            return '';
        }
    }
}
