---
# Challenge MeLi

---
## Listado de operaciones disponibles
+ Acortar URL
    https://me.li:8443/url/acortar
+ Obtener URL original
    https://me.li:8443/url/original
+ Listado de URLS existentes
    https://me.li:8443/listado_url
+ Eliminar URL existentes
    https://me.li:8443/borrar/12313
+ Eventos de manejo de URLS https://me.li:8443/eventos?fecha_desde=04-04-2021 07:00&fecha_hasta=06-04-2021 21:00
### Request Acortar URL / Obtener URL original
    {
        "url" : "https://articulo.mercadolibre.com.ar/MLA-854852461-correa-para-rodillo-de-entrenamiento-envio-gratis-_JM#reco_item_pos=19&reco_backend=machinalis-v2p-pdp-boost-v2&reco_backend_type=low_level&reco_client=vip-v2p&reco_id=20cd90d6-c99f-4ba9-ac9b-08cd853d90f9"
    }
### Ejemplo respuesta Acortar URL / URL original
    {
        "responseUrl": "https://me.li:8443/MSQLQX"
    }
### Ejemplo respuesta Listado de URLS existentes
    [
       {
            "id": 1,
            "shortUrl": "https://me.li:8443/YUJNNR",
            "longUrl": "https://articulo.mercadolibre.com.ar/MLA-854852461-correa-para-rodillo-de-entrenamiento-envio-gratis-_JM#reco_item_pos=19&reco_backend=machinalis-v2p-pdp-boost-v2&reco_backend_type=low_level&reco_client=vip-v2p&reco_id=20cd90d6-c99f-4ba9-ac9b-08cd853d90f9",
            "code": "YUJNNR"
        }
    ]
### Ejemplo de respuesta de Eventos de menjo de URLS
    [
        {
            "method": "deleteUrl",
            "statusCode": "200",
            "count": 72
        },
        {
            "method": "deleteUrl",
            "statusCode": "204",
            "count": 228
        },
        {
            "method": "handleLongUrl",
            "statusCode": "200",
            "count": 301
        },
        {
            "method": "handleRedirect",
            "statusCode": "301",
            "count": 1
        },
        {
            "method": "handleShortUrl",
            "statusCode": "422",
            "count": 300
        }
    ]
### Ejemplo respuesta de error
    {
        "message": "Validation Failed",
        "details": [
            "Debe especificar una url"
        ]
    }