using System.Text.Json.Serialization;

namespace OrderService.Models
{
    public class Order
    {
        [JsonPropertyName("id")]
        public int Id { get; set; }
        [JsonPropertyName("customerId")]
        public int CustomerId { get; set; }

    }
}