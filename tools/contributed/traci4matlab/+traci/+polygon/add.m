function add(polygonID, shape, color, fill, polygonType, layer)
%add Add a polygon to the SUMO network.
%   add(POLYGONID,SHAPE,COLOR) Adds a polygon with the id POLYGONID to the 
%   with the given shape and color. SHAPE is a cell array whose elements
%   are 2-dimensional vectors that represent the x and y coordinates of the
%   points that define the shape of the polygon. COLOR is a four-element 
%   vector whose elements range from 0 to 255, they represent the R, G, B 
%   and Alpha (unused) components of the color.
%   add(...,FILL) Is a boolean value which specifies if the polygon has to 
%   be filled.
%   add(...,POLYGONTYPE) Assigns a type POLYGONTYPE to the added polygon.
%   add(...,LAYER) Adds the poi to the given layer.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: add.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message

if nargin < 6
    layer = 0;
    if nargin < 5
        polygonType = '';
        if nargin < 4
            fill = false;
        end
    end
end

typeCompound = sscanf(constants.TYPE_COMPOUND,'%x');
typeString = sscanf(constants.TYPE_STRING,'%x');
typeColor = sscanf(constants.TYPE_COLOR,'%x');
typeInteger = sscanf(constants.TYPE_INTEGER,'%x');
typeUbyte = sscanf(constants.TYPE_UBYTE,'%x');
typePolygon = sscanf(constants.TYPE_POLYGON,'%x');

traci.beginMessage(constants.CMD_SET_POLYGON_VARIABLE, constants.ADD, polygonID,...
 1+4 + 1+4+length(polygonType) + 1+1+1+1+1 + 1+1 + 1+4 + 1+1+length(shape)*(8+8));
message.string = [message.string uint8(typeCompound) traci.packInt32(4)];
message.string = [message.string uint8(typeString) traci.packInt32(length(polygonType)) uint8(polygonType)];
message.string = [message.string uint8([typeColor color])];
message.string = [message.string uint8([typeUbyte fill])];
message.string = [message.string uint8(typeInteger) traci.packInt32(layer)];
message.string = [message.string uint8([typePolygon length(shape)])];
for i=1:length(shape)
    message.string = [message.string traci.packInt64(fliplr(shape{i}))];
end
traci.sendExact();