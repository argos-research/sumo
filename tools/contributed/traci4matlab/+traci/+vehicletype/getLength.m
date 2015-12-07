function length = getLength(typeID)
%getLength Returns the length in m of the vehicles of this type.
%   length = getLength(TYPEID) Returns the length in m of the vehicles of 
%   this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
length = traci.vehicletype.getUniversal(constants.VAR_LENGTH, typeID);