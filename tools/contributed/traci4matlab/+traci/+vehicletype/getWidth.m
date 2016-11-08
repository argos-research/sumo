function width = getWidth(typeID)
%getVehicleClass
%   vehicleClass = getVehicleClass(TYPEID) Returns the width in m of 
%   vehicles of this type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getWidth.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
width = traci.vehicletype.getUniversal(constants.VAR_WIDTH, typeID);