function emissionClass = getEmissionClass(typeID)
%getEmissionClass Returns the emission class of vehicles of this type.
%   emissionClass = getEmissionClass(TYPEID) Returns the emission class of 
%   vehicles of this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
emissionClass = traci.vehicletype.getUniversal(constants.VAR_EMISSIONCLASS, typeID);