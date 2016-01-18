function color = getColor(typeID)
%getColor Returns the color of this type of vehicle.
%   color = getColor(TYPEID)Returns the color of this type of vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getColor.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
color = traci.vehicletype.getUniversal(constants.VAR_COLOR, typeID);